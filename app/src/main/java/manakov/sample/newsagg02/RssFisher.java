package manakov.sample.newsagg02;

import android.app.IntentService;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Parcelable;
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


public class RssFisher extends IntentService {
    private String TAG = "RssFisher";

    private String inputURL;

    public RssFisher() {
        super(RssFisher.class.getSimpleName());
    }

    @Override
    public void onHandleIntent(Intent intent){
        Log.d(TAG, "Service start");

        inputURL = intent.getStringExtra(Boat.url);

        RssItemPack items = new RssItemPack();
        InputStream        inStream   = null;

        try{
            URL url = new URL(inputURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                inStream = conn.getInputStream();

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();

                Document document = builder.parse(inStream);
                Element element = document.getDocumentElement();

                NodeList nodeList = element.getElementsByTagName(Boat.item);

                for (int i = 0; i< nodeList.getLength(); i++){
                    Element entry = (Element) nodeList.item(i);

                    Element elementTitle        = (Element) entry.getElementsByTagName( "title"      ) .item(0);
                    Element elementDescription  = (Element) entry.getElementsByTagName( "description") .item(0);
                    Element elementDate         = (Element) entry.getElementsByTagName( "pubDate"    ) .item(0);
                    Element elementLink         = (Element) entry.getElementsByTagName( "link"       ) .item(0);

                    RssItem item = new RssItem(
                            elementDate        .getFirstChild().getNodeValue(),
                            elementTitle       .getFirstChild().getNodeValue(),
                            elementLink        .getFirstChild().getNodeValue(),
                            elementDescription .getFirstChild().getNodeValue()
                    );
                    items.add(item);
                }
            }
            Intent fishIntent = new Intent();
            fishIntent.setAction(Boat.fish);

            fishIntent.putExtra(Boat.fish, items);

            sendBroadcast(fishIntent);
        } catch (Exception e){
            Log.e(TAG, e.getLocalizedMessage());
        } finally {
            try{ if (inStream != null) inStream.close();} catch (IOException e){
                Log.e(TAG, e.getLocalizedMessage());
            }
        }
    }
}
