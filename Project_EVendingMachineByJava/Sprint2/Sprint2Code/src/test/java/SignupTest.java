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

public class SignupTest {
    String a;
    String b;
    String a1;
    String b1;
    @BeforeEach
    public void setup(){
        a="jason1235";
        b="12";
        a1="right";
        b1="right";
    }
    @Test
    public void Testnotinthesystem(){
         boolean output=Sign_up.check(a,b);
         assertEquals(output,true);
    }
    @Test
    public void Testinthesystem(){
        boolean output=Sign_up.check(a1,b1);
        assertEquals(output,false);
    }
    
    
}
   