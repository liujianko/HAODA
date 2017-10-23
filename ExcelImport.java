class  
{
	public List<Dhcx> importExcel(File file,String fileName){
		InputStream excelIs = null;
		Workbook workBook = null;
		Dhcx dhcx = null;
		List<Dhcx> list = new ArrayList<>();
		
		boolean endsWith = fileName.toLowerCase().endsWith("xls");
		try {
			excelIs = new FileInputStream(file);
			if(endsWith){
				workBook = new HSSFWorkbook(excelIs);
			}else{
				workBook = new XSSFWorkbook(excelIs);
			}
			
			Sheet sheet = workBook.getSheetAt(0);
			Row row = null;
			int px = 0;
			
			for (int rowIndex = 1; rowIndex < sheet.getLastRowNum(); rowIndex++) {
				px++;
				dhcx = new Dhcx();
				row = sheet.getRow(rowIndex);
				Cell nameCell = row.getCell(0); //姓名
				Cell depCell = row.getCell(1); //部门
				Cell jobCell = row.getCell(2); //职务
				Cell phoneCell = row.getCell(3); //办公电话
				
				dhcx.set("xm", nameCell.toString());
				dhcx.set("bmmc", depCell.toString());
				dhcx.set("zwmc", jobCell.toString());
				if(phoneCell != null || "".equals(phoneCell)){
					phoneCell.setCellType(Cell.CELL_TYPE_STRING);
					dhcx.set("lxdh", phoneCell.toString());
				}else{
					dhcx.set("lxdh", "");
				}
				dhcx.set("px", px);
				dhcx.set("zt", "1");
				dhcx.set("updatetime", new Date());
				list.add(dhcx);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if (workBook != null) {
					workBook.close();
				}
				if (excelIs != null) {
					excelIs.close();
				} 
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}
}
