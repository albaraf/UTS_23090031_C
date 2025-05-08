/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.p_uts_23090031_c_2025;

/**
 *
 * @author ASUS
 */
import com.mongodb.client.MongoClients;
import com.mongodb.client.*;
import com.mongodb.client.result.UpdateResult;  
import com.mongodb.client.result.DeleteResult;  
import org.bson.Document;
import java.util.*;

public class CRUD_23090031_C_2025 {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public CRUD_23090031_C_2025() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");  
        database = mongoClient.getDatabase("uts_23090031_C_2025");      
        collection = database.getCollection("coll_23090031_C_2025");   
    }

    public void createDocuments() {
       
        Document doc1 = new Document("id", 1)
                .append("nama", "Dokumen Satu")
                .append("nilai", 95);
        collection.insertOne(doc1);

        int[][] data2D = {{1, 2}, {3, 4}};
        Document doc2 = new Document("id", 2)
                .append("nama", "Dokumen Dua")
                .append("data", Arrays.asList(
                        Arrays.asList(1, 2),
                        Arrays.asList(3, 4)
                ));
        collection.insertOne(doc2);

        List<Document> penilaian = Arrays.asList(
                new Document("kriteria", "A").append("skor", 88),
                new Document("kriteria", "B").append("skor", 92)
        );
        Document doc3 = new Document("id", 3)
                .append("nama", "Dokumen Tiga")
                .append("penilaian", penilaian);
        collection.insertOne(doc3);
    }

    public void tampilkanDocuments() {
        System.out.println("======= DATA DARI MONGODB =======");
        FindIterable<Document> docs = collection.find();

        for (Document doc : docs) {
            System.out.println(doc.toJson());
            System.out.println("---------------------------------");
        }
    }

    public void updateDocument(int id, String key, Object newValue) {
        Document filter = new Document("id", id);  
        Document update = new Document("$set", new Document(key, newValue)); 

        UpdateResult result = collection.updateOne(filter, update);  
        if (result.getMatchedCount() > 0) {
            System.out.println("Dokumen dengan ID " + id + " berhasil diupdate.");
        } else {
            System.out.println("Dokumen dengan ID " + id + " tidak ditemukan.");
        }
    }

    public void deleteDocument(int id) {
        Document filter = new Document("id", id);
        DeleteResult result = collection.deleteOne(filter); 

        if (result.getDeletedCount() > 0) {
            System.out.println("Dokumen dengan ID " + id + " berhasil dihapus.");
        } else {
            System.out.println("Dokumen dengan ID " + id + " tidak ditemukan.");
        }
    }

    public void cariDokumenDenganKey(String key) {
        System.out.println("=== PENCARIAN DOKUMEN DENGAN KEY: " + key + " ===");

        FindIterable<Document> docs = collection.find();
        boolean ditemukan = false;

        for (Document doc : docs) {
            if (doc.containsKey(key)) {
                System.out.println(doc.toJson());
                System.out.println("---------------------------------");
                ditemukan = true;
            }
        }

        if (!ditemukan) {
            System.out.println("Tidak ada dokumen yang mengandung key: " + key);
        }
    }

    public static void main(String[] args) {
        CRUD_23090031_C_2025 crud = new CRUD_23090031_C_2025();

        crud.createDocuments(); 
        crud.tampilkanDocuments(); 

        crud.updateDocument(1, "nilai", 100); 
        crud.deleteDocument(2); 
        crud.tampilkanDocuments(); 

        crud.cariDokumenDenganKey("penilaian"); 
    }
}
