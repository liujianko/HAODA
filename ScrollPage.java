public void ScrollPage {
	org.codehaus.xfire.service.Service svcModel = new ObjectServiceFactory().create(IClientZgjkRecord.class);
	XFireProxyFactory factory = new XFireProxyFactory(XFireFactory.newInstance().getXFire());
	IClientZgjkRecord IClientZgjkRecord = null;
	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	Date day = new Date();
	String startDate = df.format(day);
	Calendar calendar = Calendar.getInstance();
	calendar.add(Calendar.DATE, 3);
	String endDate = df.format(calendar.getTime());
	
	try{
		IClientZgjkRecord = (IClientZgjkRecord) factory.create(svcModel,"http://146.52.1.1/XXXXX");//从接口获取数据
		String jcxx = IClientZgjkRecord.getJcxx("JD0", "2");
		Document jcDoc = DocumentHelper.parseText(jcxx);
		Element jcEle = jcDoc.getRootElement().element("ftdata");
		List jcList = jcEle.elements("record");
		Ktxx ktxxList = null;
		
		for (Iterator it = jcList.iterator(); it.hasNext();) {
			ktxxList = new Ktxx();
			Element jcRecord = (Element) it.next();
			String fjm = jcRecord.elementText("fybh"); 
			String ftdm = jcRecord.elementText("ftxh"); 
			String ftmc = jcRecord.elementText("ftmc");
			
			ktxxList.set("FJM", fjm);
			ktxxList.set("FTDM", ftdm);
			ktxxList.set("FTMC", ftmc);
			
			
			String ktxx = IClientZgjkRecord.getAhAndKtxx(startDate, endDate, fjm, ftdm);
			Document ktDoc = DocumentHelper.parseText(ktxx);
			
			Element ajEle = ktDoc.getRootElement().element("ajdata");
			List ajList = ajEle.elements("record");
			for (Iterator ajit = ajList.iterator(); ajit
					.hasNext();) {
				Element ajRecord = (Element) ajit.next();
				String ajbh = ajRecord.elementText("ajbh");
				String ah = ajRecord.elementText("ah");
				String cbfg = ajRecord.elementText("cbfg");
				String cbfgbh =  ajRecord.elementText("cbbmbh");
				
				ktxxList.set("AJBH", ajbh);
				ktxxList.set("AH", ah);
				ktxxList.set("CBFG", cbfg);
				ktxxList.set("CBFGBH", cbfgbh);
			}
			
			Element ktEle = ktDoc.getRootElement().element("ktdata");
			List ktList = ktEle.elements("record");
			for (Iterator ktit = ktList.iterator(); ktit
					.hasNext();) {
				Element ktRecord = (Element) ktit.next();
				String ktrq = ktRecord.elementText("ktrq");
				String kssj = ktRecord.elementText("ydkssj");
				String jssj = ktRecord.elementText("ydjssj");
				String ktzt = ktRecord.elementText("ktzt");
				
				ktxxList.set("KTSJ", ktrq + " " + kssj);
				ktxxList.set("JSSJ", ktrq + " " + jssj);
				ktxxList.set("KTZT", ktzt);
				ktxxList.set("CREATETIME", day);
				ktxxList.set("UPDATETIME", day);
			}
			
			if ((Ktxx.dao.findById(ktxxList.get("AJBH"))) == null && (ktxxList.get("AJBH")) != null) {
				ktxxList.save();
			}
		}
		
	}catch (MalformedURLException e) {
		e.printStackTrace();
	} catch (DocumentException e) {
		e.printStackTrace();
	}
	
	List<Ktxx> list = Ktxx.dao.find("select * from w_ktxx "
			+ "where ktsj between '" + startDate + "' and '" + endDate+"'");
	cl.setAttr("ktxxList", list);
	cl.render(getRenderUrl("ktgg"));  
}
