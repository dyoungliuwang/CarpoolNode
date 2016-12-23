package com.dyoung.carpool.node.greendao.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IdRes;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.DaoException;
import com.dyoung.carpool.node.greendao.core.DaoSession;
import com.dyoung.carpool.node.greendao.core.CarPoolNodeDao;
import com.dyoung.carpool.node.greendao.core.TripDao;

/**
 * Created by admin on 2016/11/18.
 */
@Entity
public class Trip implements Parcelable {

    @Id
    private  Long id;
    //出发城市
    @NotNull
    private String  setOut;

    //到达城市
    @NotNull
    private String arriveCity;

    @ToMany(joinProperties = {@JoinProperty(name = "id", referencedName = "tripId")
    })
    @OrderBy("date ASC")
    private List<CarPoolNode> carPoolNodes;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.setOut);
        dest.writeString(this.arriveCity);
        dest.writeTypedList(this.carPoolNodes);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSetOut() {
        return this.setOut;
    }

    public void setSetOut(String setOut) {
        this.setOut = setOut;
    }

    public String getArriveCity() {
        return this.arriveCity;
    }

    public void setArriveCity(String arriveCity) {
        this.arriveCity = arriveCity;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1179648514)
    public List<CarPoolNode> getCarPoolNodes() {
        if (carPoolNodes == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CarPoolNodeDao targetDao = daoSession.getCarPoolNodeDao();
            List<CarPoolNode> carPoolNodesNew = targetDao
                    ._queryTrip_CarPoolNodes(id);
            synchronized (this) {
                if (carPoolNodes == null) {
                    carPoolNodes = carPoolNodesNew;
                }
            }
        }
        return carPoolNodes;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 212499965)
    public synchronized void resetCarPoolNodes() {
        carPoolNodes = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 414874698)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTripDao() : null;
    }

    public Trip() {
    }

    protected Trip(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.setOut = in.readString();
        this.arriveCity = in.readString();
        this.carPoolNodes = in.createTypedArrayList(CarPoolNode.CREATOR);
    }

    @Generated(hash = 1222665272)
    public Trip(Long id, @NotNull String setOut, @NotNull String arriveCity) {
        this.id = id;
        this.setOut = setOut;
        this.arriveCity = arriveCity;
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel source) {
            return new Trip(source);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1659297594)
    private transient TripDao myDao;
}
