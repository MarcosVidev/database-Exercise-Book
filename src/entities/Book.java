package entities;

import db.DbException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Book {
    private String nameBook;
    private String author;
    private String publishedDate;
    private Double price;
    public Book() {
    }

    public Book(String nameBook, String author, String publishedDate, Double price) {
        this.nameBook = nameBook;
        this.author = author;
        this.publishedDate = publishedDate;
        this.price = price;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    public void insertBook(PreparedStatement pst, SimpleDateFormat sdf){
        try {
            pst.setString(1, nameBook);
            pst.setString(2, author);
            Date date = new Date(sdf.parse(publishedDate).getTime());
            pst.setDate(3, date);
            pst.setDouble(4, price);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected != 0) {
                System.out.println("Rows affected = " + rowsAffected);
            } else {
                System.out.println("nothing changes! ");
            }
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void listBooks(ResultSet rs) {
        try {
            while(rs.next()){
                System.out.println(rs.getInt("Id")
                        + ", "
                        +  rs.getString("Title")
                        + ", "
                        + rs.getString("Author")
                        + ", "
                        + rs.getDate("PublishedDate")
                        + ", "
                        + rs.getDouble("Price")
                );
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }
}
