package com.mstztrk.j10_5xmlwebparsing.model;

/**
 * Created by Mesut on 16.10.2017.
 */

public class Doviz {
    private String birim, isim;
    private double alis, satis;

    public String getBirim() {
        return birim;
    }

    public void setBirim(String birim) {
        this.birim = birim;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public double getAlis() {
        return alis;
    }

    public void setAlis(double alis) {
        this.alis = alis;
    }

    public double getSatis() {
        return satis;
    }

    public void setSatis(double satis) {
        this.satis = satis;
    }

    @Override
    public String toString() {
        return String.format("%s %s\nAlış: %.4f ₺\nSatış: %.4f ₺",birim,isim,alis,satis);
    }
}
