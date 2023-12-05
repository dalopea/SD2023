package Presentacion;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

import ModeloDominio.ManejadorPartidas;
//Comentario
public class ServerPartidas {

	public static void main(String[] args) {
		
		ManejadorPartidas manejadorPartidas = new ManejadorPartidas();
		ExecutorService pool=Executors.newCachedThreadPool();
		
		try(ServerSocket ss=new ServerSocket(55555)){
			
			while(true) {
				
				try{
					Socket s=ss.accept();
					HiloServerPartidas hsp=new HiloServerPartidas(s,manejadorPartidas);
					pool.execute(hsp);
				}catch(IOException e) {
					e.printStackTrace();
				}
				
			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			pool.shutdown();
		}

	}

}
