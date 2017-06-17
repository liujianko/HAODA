/**
 * 前台页面传到后台是一个'table'
 * 导出Excel功能
 */
public void getTableHtml(){
	String tableHtml = getPara("data");
	tableHtml = EncodingUtil.getInstance().Iso88591ToUtf8(tableHtml); //解决前台过来的中文
	String root = getSession().getServletContext().getRealPath("/"); //获取跟路径
	String url = new ExcelExport().exportExcel(tableHtml,root);
	setAttr("url", url);
	renderJson();
	
}


public String exportExcel(String tableHtml,String root){
	StringBuffer sb = new StringBuffer();
	sb.append("<html xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:x=\"urn:schemas-microsoft-com:office:excel\" xmlns=\"http://www.w3.org/TR/REC-html40\">");
	sb.append("<head>");
	sb.append("<meta http-equiv=Content-Type content=\"text/html; charset=gb2312\">");
	sb.append("<style>");
	sb.append("<!--table {mso-displayed-decimal-separator:\"\\.\";mso-displayed-thousand-separator:\"\\,\";}");	
	sb.append("@page {margin:1.0in .75in 1.0in .75in;mso-header-margin:.5in;mso-footer-margin:.5in;}");
	sb.append("tr {mso-height-source:auto;mso-ruby-visibility:none;}");
	sb.append("col {mso-width-source:auto;mso-ruby-visibility:none;}");
	sb.append("br {mso-data-placement:same-cell;}");
	sb.append(".style0 {mso-number-format:\"\\@\";text-align:general;vertical-align:middle;white-space:nowrap;mso-rotate:0;mso-background-source:auto;mso-pattern:auto;color:windowtext;font-size:12.0pt;font-weight:400;font-style:normal;text-decoration:none;font-family:宋体;mso-generic-font-family:auto;mso-font-charset:134;border:none;mso-protection:locked visible;mso-style-name:常规;mso-style-id:0;}");
	sb.append("tbody td {mso-style-parent:style0;padding-top:1px;padding-right:1px;padding-left:1px;mso-ignore:padding;color:windowtext;font-size:12.0pt;font-weight:400;font-style:normal;text-decoration:none;font-family:宋体;mso-generic-font-family:auto;mso-font-charset:134;mso-number-format:General;text-align:general;vertical-align:middle;border:none;mso-background-source:auto;mso-pattern:auto;mso-protection:locked visible;white-space:nowrap;mso-rotate:0;}");
	sb.append("thead td {mso-style-parent:style0;background-color:silver;}");
	sb.append("ruby {ruby-align:left;}");
	sb.append("rt {color:windowtext;font-size:9.0pt;font-weight:400;font-style:normal;text-decoration:none;font-family:宋体;mso-generic-font-family:auto;mso-font-charset:134;mso-char-type:none;display:none;}");
	sb.append("-->");
	sb.append("</style>");
	sb.append("<!--[if gte mso 9]><xml>");
	sb.append("<x:ExcelWorkbook>");
	sb.append("<x:ExcelWorksheets>");
	sb.append("<x:ExcelWorksheet>");
	sb.append("<x:Name>数据导出</x:Name>");
	sb.append("<x:WorksheetOptions>");
	sb.append("<x:DefaultRowHeight>285</x:DefaultRowHeight>");
	sb.append("<x:Selected/>");
	sb.append("<x:Panes><x:Pane><x:Number>3</x:Number><x:ActiveRow>6</x:ActiveRow><x:ActiveCol>1</x:ActiveCol></x:Pane></x:Panes>");
	sb.append("<x:ProtectContents>False</x:ProtectContents>");
	sb.append("<x:ProtectObjects>False</x:ProtectObjects>");
	sb.append("<x:ProtectScenarios>False</x:ProtectScenarios>");
	sb.append("</x:WorksheetOptions>");
	sb.append("</x:ExcelWorksheet>");
	sb.append("</x:ExcelWorksheets>");
	sb.append("<x:WindowHeight>11145</x:WindowHeight>");
	sb.append("<x:WindowWidth>21435</x:WindowWidth>");
	sb.append("<x:WindowTopX>0</x:WindowTopX>");
	sb.append("<x:WindowTopY>75</x:WindowTopY>");
	sb.append("<x:ProtectStructure>False</x:ProtectStructure>");
	sb.append("<x:ProtectWindows>False</x:ProtectWindows>");
	sb.append("</x:ExcelWorkbook>");
	sb.append("</xml><![endif]-->");
	sb.append("</head>");
	sb.append("<body link=blue vlink=purple class=xl24>");
	sb.append(tableHtml);
	sb.append("</body></html>");
	String htmls = sb.toString();
	
	OutputStreamWriter osw = null;
	FileOutputStream fos = null;
	String saveDir = "";
	String fileName = "";
	String url = "";
	try {
		saveDir= root + "/gridExport";
		File dir = new File(saveDir);
		if (!(dir.exists() && dir.isDirectory())) {
			dir.mkdir();
		}
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		fileName = sf.format(new Date()) + ".xls";
		fos = new FileOutputStream(saveDir + "/" +fileName);
		osw = new OutputStreamWriter(fos, "GBK");
		osw.write(htmls);
		url = "gridExport" + "/" + fileName;
		if (osw != null) {
			osw.close();
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	return url;
}