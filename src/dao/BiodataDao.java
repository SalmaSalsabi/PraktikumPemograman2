/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.MySqlConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import biodata.Biodata;

/**
 *
 * @author salma
 */

public class BiodataDao {
    public int insert(Biodata biodata) {
        int result = -1;
        try (Connection connection = MySqlConnection.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "Insert into biodata(id, nama, no_telepon, jenis_kelamin, alamat) values (?, ?, ?, ?, ?)");
            statement.setString(1, biodata.getId()); 
            statement.setString(2, biodata.getNama()); 
            statement.setString(3, biodata.getNoTelepon()); 
            statement.setString(4, biodata.getJenisKelamin()); 
            statement.setString(5, biodata.getAlamat());
            
            result = statement.executeUpdate();

            System.out.println("Insert data: " + biodata.getId() + " " + biodata.getNama() + " "
                    + biodata.getNoTelepon() + " " + biodata.getJenisKelamin() + " " + biodata.getAlamat());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
    
    public int update(Biodata biodata) {
        int result = -1;
        try (Connection connection = MySqlConnection.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "update biodata set nama = ?, no_telepon = ?, jenis_kelamin = ?, alamat = ? where id = ?");

            statement.setString(1, biodata.getNama());
            statement.setString(2, biodata.getNoTelepon());
            statement.setString(3, biodata.getJenisKelamin()); 
            statement.setString(4, biodata.getAlamat()); 
            statement.setString(5, biodata.getId()); 
            result = statement.executeUpdate();
            System.out.println("Update data: " + biodata.getId() + " " + biodata.getNama() + " "
                    + biodata.getNoTelepon() + " " + biodata.getJenisKelamin() + " " + biodata.getAlamat());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }

    public int delete(Biodata biodata) {
        int result = -1;
        try (Connection connection = MySqlConnection.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("delete from biodata where id = ?");
            statement.setString(1, biodata.getId());
            result = statement.executeUpdate();

            System.out.println("Delete data: " + biodata.getId() + " " + biodata.getNama() + " "
                    + biodata.getNoTelepon() + " " + biodata.getJenisKelamin() + " " + biodata.getAlamat());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
    
    public List<Biodata> findAll() {
        List<Biodata> list = new ArrayList<>();
        try (
                Connection connection = MySqlConnection.getInstance().getConnection();
                Statement statement = connection.createStatement();) {
            try (ResultSet resultSet = statement.executeQuery("select * from biodata")) {
                while (resultSet.next()) {
                    Biodata biodata = new Biodata();
                    biodata.setId(resultSet.getString("id")); 
                    biodata.setNama(resultSet.getString("nama")); 
                    biodata.setNoTelepon(resultSet.getString("no_telepon"));
                    biodata.setJenisKelamin(resultSet.getString("jenis_kelamin"));
                    biodata.setAlamat(resultSet.getString("alamat"));
                    list.add(biodata);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }

    public Biodata select(String column, String value) {
        // Membuat object biodata untuk menyimpan data
        Biodata biodata = new Biodata();

        // Try with resources untuk membuat koneksi ke database
        try (
                // Membuat koneksi ke database
                Connection connection = MySqlConnection.getInstance().getConnection();
                // Statement untuk mengirim query ke database
                Statement statement = connection.createStatement();
            ) {
            // Membuat ResultSet untuk menyimpan hasil dari eksekusi query
            try (ResultSet resultSet = statement.executeQuery("select * from biodata where " + column+ " = '" + value + "'");) {
                // Looping untuk mengambil semua data dari database
                while (resultSet.next()) {
                    // Set nilai dari object biodata
                    biodata.setId(resultSet.getString("id")); // id
                    biodata.setNama(resultSet.getString("nama")); // nama
                    biodata.setNoTelepon(resultSet.getString("no_telepon")); // no_telepon
                    biodata.setJenisKelamin(resultSet.getString("jenis_kelamin")); // jenis_kelamin
                    biodata.setAlamat(resultSet.getString("alamat")); // alamat
                }
            } catch (SQLException e) {
                // Print error jika terjadi error
                e.printStackTrace();
            }
        } catch (SQLException e) {
            // Print error jika terjadi error
            e.printStackTrace();
        }

        // Kembalikan nilai biodata
        return biodata;
    }
}