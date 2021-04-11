package Model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
public class AppTest {
    List<Exchange> exchanges=new ArrayList<>();
    List<Exchange> exchangesY=new ArrayList<>();

    private static ByteArrayInputStream in;
    @BeforeEach
    public void setup(){
        App.setupall(exchanges,exchangesY);
        
    }
    @Test
    public void check(){
        assertEquals(exchanges.size(),30);
        assertEquals(exchangesY.size(),30);
    }
    
}


   