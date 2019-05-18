package manakov.sample.newsaggregator03;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class RssItemFisherService extends IntentService {
    private String TAG = this.getClass().getName();
    private String currentTitle;
    private String currentUrl;
    private int    currentId;

    public RssItemFisherService() {
        super(RssItemFisherService.class.getSimpleName());
    }

    @Override
    public void onHandleIntent(Intent intent){
        while (true)
        {
            NewsAggApplication application = (NewsAggApplication) getApplication();
            currentTitle = intent.getExtras().getString("Title");
            currentUrl = intent.getExtras().getString("Url");
            currentId = intent.getExtras().getInt("Id");

            InputStream inputStream = null;
            Intent broadcastIntent = new Intent();
            try {
                URL url = new URL(currentUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    application
                            .dataBase
                            .rssItemDao()
                            .deleteAllByUrlId(currentId);
                    inputStream = conn.getInputStream();

                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();

                    Document document = builder.parse(inputStream);
                    Element element = document.getDocumentElement();

                    NodeList nodeList = element.getElementsByTagName(NewsAggApplication.item);

                    for (int j = 0; j < nodeList.getLength(); j++) {
                        Element entry = (Element) nodeList.item(j);

                        Element elementTitle = (Element) entry.getElementsByTagName("title").item(0);
                        Element elementDate = (Element) entry.getElementsByTagName("pubDate").item(0);
                        Element elementLink = (Element) entry.getElementsByTagName("link").item(0);
                        Element elementDescription = (Element) entry.getElementsByTagName("description").item(0);

                        RssItem item = new RssItem(
                                elementTitle.getFirstChild().getNodeValue(),
                                elementDate.getFirstChild().getNodeValue(),
                                elementLink.getFirstChild().getNodeValue(),
                                elementDescription.getFirstChild().getNodeValue(),
                                currentId
                        );
                        application
                                .dataBase
                                .rssItemDao()
                                .insertAll(
                                        item
                                );
                    }
                    broadcastIntent.setAction("Success" + currentId);
                }
                broadcastIntent.putExtra("urlID", currentUrl);
                sendBroadcast(broadcastIntent);
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage());
            } finally {
                try {
                    if (inputStream != null) inputStream.close();
                } catch (IOException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                }
            }
            try {
                    Thread.sleep(
                        Math.round(
                                (application
                                        .dataBase
                                        .timeOffsetDao()
                                        .get()
                                        .get(0)
                                        .getDelay())
                        ) * 60 * 1000
                    );
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
            }
        }
    }
}
