package com.whu.DataRead;

import java.io.File;

public class FileNames {
	private String filenames[];
	public void init(String path)
	{
		File f = new File(path+"/picList");
		File[] list = f.listFiles();
		String[] _filenames = new String[list.length/2];
		int tag = 0;
		for(File file : list)
		{
			if(file.getName().toUpperCase().endsWith(".TXT"))
			{
				_filenames[tag] = file.getName();
				++ tag;
			}
			
		}
		filenames = _filenames;
	}
	public String[] getFileNames()
	{
		return filenames;
	}
}
