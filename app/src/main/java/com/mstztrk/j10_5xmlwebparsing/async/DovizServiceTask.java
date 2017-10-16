package com.mstztrk.j10_5xmlwebparsing.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mstztrk.j10_5xmlwebparsing.model.Doviz;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Mesut on 16.10.2017.
 */

public class DovizServiceTask extends AsyncTask<String, String, ArrayList<Doviz>> {

    private Context context;
    private ProgressDialog progressDialog;
    private ListView listView;

    public DovizServiceTask(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;
    }

    @Override
    protected void onPreExecute() {
        //ilk çalışan metottur.
        //task çalışmadan önce yapılacak hazırlık işlemleri burada tanımlanır.
        progressDialog = progressDialog.show(context, "Lütfen Bekleyiniz", "Suncuyla bağlantı kuruluyor", true, true);
    }

    @Override
    protected ArrayList<Doviz> doInBackground(String... params) {
        //Yazılması zorunlu tek method
        //1. parametrenin tipinde çalışır.
        //execute metoduna verilen arg ile çağırılır.
        //geri donen tip 3. parametre tipindedir. onpostexecute metoduna da parametre olarak gönderilir.
        //publishprogress metodu ile onprogressupdate metoduna bilgi gönderilebilir.
        ArrayList<Doviz> dovizler = new ArrayList<>();
        HttpURLConnection baglanti = null;
        try {
            URL url = new URL(params[0]);
            baglanti = (HttpURLConnection) url.openConnection();
            if (baglanti.getResponseCode() == HttpURLConnection.HTTP_OK) {
                publishProgress("Bağlantı kuruldu\nDöviz kurları okunuyor");

                BufferedInputStream stream = new BufferedInputStream(baglanti.getInputStream());

                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

                Document document = documentBuilder.parse(stream);
                NodeList dovizNodeList = document.getElementsByTagName("Currency");
                for (int i = 0; i < dovizNodeList.getLength() - 1; i++) {
                    Element element = (Element) dovizNodeList.item(i);
                    Doviz yeniDoviz = new Doviz();
                    String birim = element.getElementsByTagName("Unit").item(0).getFirstChild().getNodeValue();
                    yeniDoviz.setBirim(birim);

                    String isim = element.getElementsByTagName("Isim").item(0).getFirstChild().getNodeValue();
                    yeniDoviz.setIsim(isim);

                    double alis = Double.valueOf(element.getElementsByTagName("ForexBuying").item(0).getFirstChild().getNodeValue());
                    double satis = Double.valueOf(element.getElementsByTagName("ForexSelling").item(0).getFirstChild().getNodeValue());

                    yeniDoviz.setAlis(alis);
                    yeniDoviz.setSatis(satis);

                    dovizler.add(yeniDoviz);
                }
                publishProgress("Liste güncelleniyor");
            } else {
                publishProgress("İnternet bağlantısı kurulamadı");
            }
            return dovizler;
        } catch (Exception ex) {
            Toast.makeText(context, "XML Okuma Hatası", Toast.LENGTH_SHORT).show();
            Log.e("DovizTask", ex.getMessage());
            return null;
        } finally {
            if (baglanti != null)
                baglanti.disconnect();
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        //2. parametre tipi buraya arguman olarak gönderilir
        //doInBackground metodunun çerisinden buraya publishprogress metodu ile arguman gönderilir.
        progressDialog.setMessage(values[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<Doviz> dovizler) {
        //Parametre olarak doInBackground sonucunu alır (3. parametreyi alır);
        progressDialog.setMessage("İşlem Tamamlandı");
        if (dovizler != null) {
            ArrayAdapter<Doviz> dovizArrayAdapter = new ArrayAdapter<Doviz>(context, android.R.layout.simple_list_item_1, dovizler);
            listView.setAdapter(dovizArrayAdapter);
            progressDialog.cancel();
        }
    }

    @Override
    protected void onCancelled(ArrayList<Doviz> dovizs) {
        super.onCancelled(dovizs);
        //herhangi bir sebeple task iptal edilirse bu methot tetiklenir.
        //Burada kullandığınız kaynakları temizleyebilirsiniz.
    }
}
