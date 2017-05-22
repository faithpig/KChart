package com.whu.DataRead;

import java.util.List;
import java.io.File;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import com.whu.KChartDemo.*;
public class FileChoose {
	private String firstname;
	private String webpath;
	public FileChoose(){
	}
	public FileChoose(String webPath){
		this.webpath = webPath;
	}
	public void chooseURL()
	{
		
//		FileNameExtensionFilter filter=new FileNameExtensionFilter("*.txt", "txt");
//		JFileChooser jf = new JFileChooser("C:/");
//		jf.setFileFilter(filter);
//		jf.setFileSelectionMode(JFileChooser.FILES_ONLY); 
//		int result=jf.showOpenDialog(null);
//		
//		if (result==JFileChooser.APPROVE_OPTION) {
//			
////			File f_ = jf.getSelectedFile();
////			File folder = new File(f_.getParent()+"\\JPEG_DATA\\");
////			
////			if( !folder.exists()) folder.mkdirs();
//			
//		}
		File[] file_list = null;
		file_list = (new File(webpath+"/picList")).listFiles();
		System.out.println(file_list.length);
		if(file_list != null)
		{
			int tag = 0;
			for(File f : file_list)
			{
				if(f!=null) {System.out.println("success");}else {System.out.println("fail");}
				if(f.getName().toUpperCase().endsWith(".TXT"))
				{
					if(tag == 0) firstname = f.getName();
					tag++;
					//----------»­Í¼;
//					DrawP dP = new DrawP(f);
//					Thread draw = new Thread(dP);
//					threadList.add(draw);
//					draw.start();
					TextRead tx = new TextRead();
					tx.init(f);
					List<KData> list = new ArrayList<KData>();
					int list_size = tx.get_issuse_date().length;
					for(int i=0;i<list_size;i++){
						KData k = new KData(tx.get_stock_name(), tx.get_issuse_date()[i], 
								tx.get_open_value()[i], tx.get_high_value()[i], tx.get_low_value()[i], 
								tx.get_close_value()[i], tx.get_volume_value()[i],tx.get_turnover_value()[i]);
						list.add(k);
					}
					String beginDate = "0000-00-00";
					String endDate = "0000-00-00";
					SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sf2 = new SimpleDateFormat("MM/dd/yyyy");
					try {
						beginDate = sf1.format(sf2.parse(tx.get_issuse_date()[0]));
						Date tempDate = sf2.parse(tx.get_issuse_date()[list_size-1]);
						Calendar cal = Calendar.getInstance();
						cal.setTime(tempDate);
					    cal.add(Calendar.DATE,1);
					    tempDate = cal.getTime();
					    endDate = sf1.format(tempDate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					KChart chart = new KChart(list, beginDate, endDate);
					chart.savaAsPic(tx.get_jpeg_path());
					System.out.println(tx.get_jpeg_path());
				}
			}
			
		}
		
	}
public String getFirstFileName(){
		
		return firstname;
	}

}

/*class DrawP implements Runnable{
	private File f ;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		TextRead tx = new TextRead();
		tx.init(f);
		if(tx.get_high_value().length ==0) return;
		List<KData> list = new ArrayList<KData>();
		int list_size = tx.get_issuse_date().length;
		for(int i=0;i<list_size;i++){
			KData k = new KData(tx.get_stock_name(), tx.get_issuse_date()[i], 
					tx.get_open_value()[i], tx.get_high_value()[i], tx.get_low_value()[i], 
					tx.get_close_value()[i], tx.get_volume_value()[i],tx.get_turnover_value()[i]);
			list.add(k);
		}
		String beginDate = "0000-00-00";
		String endDate = "0000-00-00";
		SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sf2 = new SimpleDateFormat("MM/dd/yyyy");
		try {
			beginDate = sf1.format(sf2.parse(tx.get_issuse_date()[0]));
			Date tempDate = sf2.parse(tx.get_issuse_date()[list_size-1]);
			Calendar cal = Calendar.getInstance();
			cal.setTime(tempDate);
		    cal.add(Calendar.DATE,1);
		    tempDate = cal.getTime();
		    endDate = sf1.format(tempDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		KChart chart = new KChart(list, beginDate, endDate);
		System.out.println(tx.get_jpeg_path());
		chart.savaAsPic(tx.get_jpeg_path());
	}
	public DrawP(File f)
	{
		this.f = f;
	}
	
	
}*/
