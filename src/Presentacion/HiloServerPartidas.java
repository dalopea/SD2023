package Presentacion;

import java.io.*;
import java.net.*;
import java.util.*;

public class HiloServerPartidas extends Thread {

	private Socket s;
	
	
	public HiloServerPartidas(Socket s,HashMap<String,Integer> partidas) {
		this.s=s;
	}
	
	
	public void run() {

			try {
				BufferedReader bf=new BufferedReader(new InputStreamReader(s.getInputStream()));
				PrintWriter pw=new PrintWriter(s.getOutputStream());
				
				
				String peticion=bf.readLine();
				
				
			}catch(IOException e) {
				e.printStackTrace();
			}finally {
				try {
					s.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		
	}
}
