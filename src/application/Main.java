package application;
import db.DB;
import db.DbException;
import entities.Book;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Connection conn = null;
        Statement st = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Insert the amount of books: ");
        int bookQuantity = sc.nextInt();
        int rowsAffected = 0;
        try {
            Book db = new Book();
            conn = DB.getConnection();
            pst = conn.prepareStatement(
                    "INSERT INTO book "
                    + "(Title, Author, PublishedDate, Price)"
                    + " VALUES "
                    + "(?, ?, ?, ?)");
            while(bookQuantity > 0){
                sc.nextLine();
                System.out.println("Insert the name of book: ");
                String nameBook = sc.nextLine();
                System.out.println("Insert the name of author: ");
                String nameAuthor = sc.nextLine();
                System.out.println("Insert the date (dd/MM/yyyy): ");
                String publishedDate = sc.nextLine();
                System.out.println("Insert the price: ");
                double price = sc.nextDouble();
                db = new Book(nameBook, nameAuthor, publishedDate, price);
                db.insertBook(pst, sdf);
                bookQuantity --;
            }
            st = conn.createStatement();
            rs = st.executeQuery("select * from book");
            System.out.println("----Books----");
            db.listBooks(rs);
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeresultSet(rs);
            DB.closeStatement(st);
            DB.closeStatement(pst);
            DB.closeConnection();
            sc.close();
        }
    }
}