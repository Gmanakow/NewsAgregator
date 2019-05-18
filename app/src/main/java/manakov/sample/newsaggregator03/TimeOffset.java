package manakov.sample.newsaggregator03;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TimeOffset {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private double delay;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public TimeOffset(double delay) {
        this.delay = delay;
    }

    public void setDelay(double delay) {
        this.delay = delay;
    }

    public double getDelay() {
        return delay;
    }
}
