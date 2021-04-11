import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.io.*;
import java.util.*;
import java.lang.String;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
    String a;
    String b;
    String a1;
    String b1;
    @BeforeEach
    public void setup(){
        a="None_in_database";
        b="123";
        a1="right";
        b1="right";
    }
    @Test
    void Testwrong_account(){ 
        int output=Login.check(a,b);
        assertEquals(output,0);
    }
    // @Test
    // void Testright_account(){ 
    //     int output=Login.check(a1,b1);
    //     assertEquals(output,1);
    // }
}
   