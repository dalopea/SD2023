package Presentacion;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ServerPartidas {

	public static void main(String[] args) {
		
		HashMap<String,Integer> partidas=new HashMap<String,Integer>();
		ExecutorService pool=Executors.newCachedThreadPool();
		
		try(ServerSocket ss=new ServerSocket(55555)){
			
			while(true) {
				
				try{
				
					/*
					 * Necesitare saber como hacermos para que los cambios del hilo se vean aquí, static igual?
					 * */
					Socket s=ss.accept();
					HiloServerPartidas hsp=new HiloServerPartidas(s,partidas);
					pool.execute(hsp);
					
					
					
					
					
				}catch(IOException e) {
					e.printStackTrace();
				}
				
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.shutdown();
		}

	}

}
