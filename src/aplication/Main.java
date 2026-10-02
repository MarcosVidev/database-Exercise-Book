package aplication;
import db.DB;
import db.DbException;
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
            DB db = new DB();
            conn = DB.getConnection();
            pst = conn.prepareStatement(
                    "INSERT INTO book "
                    + "(Title, Author, PublishedDate, Price)"
                    + "VALUES "
                    + "(?, ?, ?, ?)");
            while(bookQuantity > 0){
                sc.nextLine();
                System.out.println("Insert the name of book: ");
                String nameBook = sc.nextLine();
                pst.setString(1, nameBook);
                System.out.println("Insert the name of author: ");
                String nameAuthor = sc.nextLine();
                pst.setString(2, nameAuthor);
                System.out.println("Insert the date: ");
                String publishedDate = sc.nextLine();
                System.out.println("Insert the price: ");
                Double price = sc.nextDouble();
                db = new DB(nameBook, nameAuthor, publishedDate, price);
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
            DB.closeStatement(pst);
            DB.closeConnection();
        }
    }
}