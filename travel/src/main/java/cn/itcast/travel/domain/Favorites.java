package cn.itcast.travel.domain;

import java.io.Serializable;

/**
 * 收藏实体类
 */
public class Favorites implements Serializable {
    private int rid;//旅游线路id
    private String rname;//旅游线路名称
    private double price;//价格
    private String rimage;//缩略图 private

    public Favorites() {
    }

    public Favorites(int rid, String rname, double price, String rimage) {
        this.rid = rid;
        this.rname = rname;
        this.price = price;
        this.rimage = rimage;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRimage() {
        return rimage;
    }

    public void setRimage(String rimage) {
        this.rimage = rimage;
    }

    @Override
    public String toString() {
        return "Favorites{" +
                "rid=" + rid +
                ", rname='" + rname + '\'' +
                ", price=" + price +
                ", rimage='" + rimage + '\'' +
                '}';
    }
}
