package com.dyoung.carpool.node.greendao.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.dyoung.carpool.node.greendao.core.CarPoolNodeDao;
import com.dyoung.carpool.node.greendao.core.DaoSession;
import com.dyoung.carpool.node.greendao.core.TripDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;

/**
 * Created by admin on 2016/11/16.
 */
@Entity
public class CarPoolNode implements Parcelable {

    @Id
    private Long id;
    private Long tripId;
    //拼车状态 1已上车，0：未上车
    private  Integer status;
    //人数
    private  Integer num;
    @Convert(converter = NoteTypeConverter.class, columnType = String.class)
    private NodeType nodeType;
    @NotNull
    private String number;
    private String mark;
    private Long date;
    //发车时间
    private Long rideTime;
    @ToOne(joinProperty = "tripId")
    private Trip trip;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.tripId);
        dest.writeValue(this.status);
        dest.writeValue(this.num);
        dest.writeInt(this.nodeType == null ? -1 : this.nodeType.ordinal());
        dest.writeString(this.number);
        dest.writeString(this.mark);
        dest.writeValue(this.date);
        dest.writeValue(this.rideTime);
        dest.writeParcelable(this.trip, flags);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTripId() {
        return this.tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getNum() {
        return this.num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public NodeType getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMark() {
        return this.mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Long getDate() {
        return this.date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Long getRideTime() {
        return this.rideTime;
    }

    public void setRideTime(Long rideTime) {
        this.rideTime = rideTime;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 113654134)
    public Trip getTrip() {
        Long __key = this.tripId;
        if (trip__resolvedKey == null || !trip__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TripDao targetDao = daoSession.getTripDao();
            Trip tripNew = targetDao.load(__key);
            synchronized (this) {
                trip = tripNew;
                trip__resolvedKey = __key;
            }
        }
        return trip;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 269213208)
    public void setTrip(Trip trip) {
        synchronized (this) {
            this.trip = trip;
            tripId = trip == null ? null : trip.getId();
            trip__resolvedKey = tripId;
        }
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
    @Generated(hash = 1830327166)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCarPoolNodeDao() : null;
    }

    public CarPoolNode() {
    }

    protected CarPoolNode(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.tripId = (Long) in.readValue(Long.class.getClassLoader());
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.num = (Integer) in.readValue(Integer.class.getClassLoader());
        int tmpNodeType = in.readInt();
        this.nodeType = tmpNodeType == -1 ? null : NodeType.values()[tmpNodeType];
        this.number = in.readString();
        this.mark = in.readString();
        this.date = (Long) in.readValue(Long.class.getClassLoader());
        this.rideTime = (Long) in.readValue(Long.class.getClassLoader());
        this.trip = in.readParcelable(Trip.class.getClassLoader());
    }

    @Generated(hash = 1291834657)
    public CarPoolNode(Long id, Long tripId, Integer status, Integer num,
            NodeType nodeType, @NotNull String number, String mark, Long date,
            Long rideTime) {
        this.id = id;
        this.tripId = tripId;
        this.status = status;
        this.num = num;
        this.nodeType = nodeType;
        this.number = number;
        this.mark = mark;
        this.date = date;
        this.rideTime = rideTime;
    }

    public static final Creator<CarPoolNode> CREATOR = new Creator<CarPoolNode>() {
        @Override
        public CarPoolNode createFromParcel(Parcel source) {
            return new CarPoolNode(source);
        }

        @Override
        public CarPoolNode[] newArray(int size) {
            return new CarPoolNode[size];
        }
    };
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 2076129708)
    private transient CarPoolNodeDao myDao;
    @Generated(hash = 916729084)
    private transient Long trip__resolvedKey;
}