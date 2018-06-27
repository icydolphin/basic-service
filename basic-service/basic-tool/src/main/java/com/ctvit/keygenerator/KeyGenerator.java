package com.ctvit.keygenerator;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author CTVIT
 *	主键生产器类，为各个数据表生成主键
 */
public class KeyGenerator<T> {
	
	private static int randomNum = 100;
	/**
	 * 新媒体内容ID顺序号
	 */
	private static int newRandomNum = 0;
	/**
	 * 新媒体内容集成平台系统标识
	 */
	private final String systemCode = "CH00";
	
	private final String columnCode = "00000";
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHss");
	
	private String preKey = "";
	
	public KeyGenerator() {

	}

	/**
	 * 返回主键  4位类名+13位毫秒数+3位递增数字
	 * @param className  类名s
	 * @return  PK
	 */
	public String getKey(T baseData) throws Exception {

		String keyStr = "";
		String className = null;
		if (baseData != null) {
			className = baseData.getClass().getName();
			className = className.substring(className.lastIndexOf(".") + 1);
			className = this.getTargetType(className);
		}

		try {
			
			if (className == null) {
				className = "Xxxx";
			} else if (className.length() >= 4) {
				className = className.substring(0, 4);
			} else {
				className = className + "Xxxx";
				className = className.substring(0, 4);
			}

			keyStr = keyStr + className;
			//Date date = new Date();
			synchronized (KeyGenerator.class){
				if (randomNum >= 999) {
					randomNum = 100;
				} else {
					randomNum = randomNum + 1;
				}
				keyStr = keyStr + System.currentTimeMillis() + randomNum;
			}
			//第二次校验key是否与上次的key相同
			if (keyStr.equals(preKey)) {
				Thread.currentThread().sleep(1);
			} else {
				preKey = keyStr;
			}

		} catch (Exception e) {
			keyStr = null;
			throw new Exception("get PK error");
		}
		return keyStr;
	}
	
	public String getKey(T baseData,Timestamp time) throws Exception {

		String keyStr = "";
		String className = null;
		if (baseData != null) {
			className = baseData.getClass().getName();
			className = className.substring(className.lastIndexOf(".") + 1);
			className = this.getTargetType(className);
		}

		try {
			
			if (className == null) {
				className = "Xxxx";
			} else if (className.length() >= 4) {
				className = className.substring(0, 4);
			} else {
				className = className + "Xxxx";
				className = className.substring(0, 4);
			}

			keyStr = keyStr + className;
			//Date date = new Date();
			synchronized (KeyGenerator.class){
				if (randomNum >= 999) {
					randomNum = 100;
				} else {
					randomNum = randomNum + 1;
				}
				keyStr = keyStr + time.getTime() + randomNum;
			}
			//第二次校验key是否与上次的key相同
			if (keyStr.equals(preKey)) {
				Thread.currentThread().sleep(1);
			} else {
				preKey = keyStr;
			}

		} catch (Exception e) {
			keyStr = null;
			throw new Exception("get PK error");
		}
		return keyStr;
	}
	
	public String getTargetType(String className) throws Exception {
		String targetType = "";
		
		try {
			if(className.equals("TabArticle")) {targetType = "ARTI";}
			else if(className.equals("TabArticleAlbum")) {targetType = "ARTA";}
			else if(className.equals("TabPhoto")) {targetType = "PHOT";}
			else if(className.equals("TabPhotoAlbum")) {targetType = "PHOA";}
			else if(className.equals("TabPhotoSizeDetails")) {targetType = "PHSD";}
			else if(className.equals("TabVideo")) {targetType = "VIDE";}
			else if(className.equals("TabVideoWithBLOBs")) {targetType = "VIDE";}
			else if(className.equals("Audio")) {targetType = "AUDI";}
			else if(className.equals("AudioWithBLOBs")) {targetType = "AUDI";}
			else if(className.equals("AudioExample")) {targetType = "AUDI";}
			else if(className.equals("AudioAlbum")) {targetType = "AUDA";}
			else if(className.equals("AudioAlbumExample")) {targetType = "AUDA";}
			else if(className.equals("TabVideoInfo")) {targetType = "VIIN";}
			else if(className.equals("TabVideoAlbum")) {targetType = "VIDA";}
			else if(className.equals("TabVideoAlbumInfo")) {targetType = "VIDI";}
			else if(className.equals("TabVideoRelated")) {targetType = "VRRE";}//视频相关新闻链接
			else if(className.equals("TabVideoHotWords")) {targetType = "VRHW";}//视频相关热词

			else if(className.equals("TabArticleRelated")) {targetType = "ARRE";}
			else if(className.equals("TabPhotoAlbumRelated")) {targetType = "PARE";}
			else if(className.equals("TabPhotoAlbumRelation")) {targetType = "PARN";}
			else if(className.equals("TabVideoAlbumRelation")) {targetType = "PVRN";}
			else if(className.equals("TabArticleHotWords")) {targetType = "ARHW";}
			else if(className.equals("TabCustomTopic")) {targetType = "TCTC";}

			
			else if(className.equals("TabPageArticleRelations")) {targetType = "PARS";}
			else if(className.equals("TabPagePhotoAlbumRelations")) {targetType = "PPAR";}
			else if(className.equals("TabPageVideoAlbumRelations")) {targetType = "PVAR";}
			else if(className.equals("TabPagePhotoRelations")) {targetType = "PHAR";}
			else if(className.equals("TabCatalogCategories")) {targetType = "CATE";}   //编目分类
			else if(className.equals("TabCatalogClassRelations")) {targetType = "CATC";}//编目分类关系
			
			else if(className.equals("TabCatalogArticleRelations")) {targetType = "CATR";}//正文与编目关系
			else if(className.equals("TabCatalogPhotoalbRelations")) {targetType = "CPAR";}//图集与编目关系
			else if(className.equals("TabCatalogPhotoRelations")) {targetType = "CPTR";}//图片与编目关系
			else if(className.equals("TabCatalogVideoalbRelations")) {targetType = "CVAR";}//视频集与编目关系
			else if(className.equals("TabCatalogVideoRelations")) {targetType = "CVTR";}//视频编目关系
			
			
			

			else if(className.equals("TimerTaskBean")){targetType = "TTSB";}//yrj 定时任务
			else if(className.equals("TabRealTimeNotice")){targetType = "TRTN";}//yrj 公告
			else if(className.equals("TabRealTimeSysmessage")){targetType = "TRTS";}//yrj 系统消息
			else if(className.equals("TabBatchIssueInfo")){targetType = "TBII";}//yrj 批量发布
			else if(className.equals("TabBatchIssueMain")){targetType = "TBIM";}//yrj 批量发布
			else if(className.equals("TabFileUploadInfo")){targetType = "TFUI";}//yrj 文件上传基本信息
			else if(className.equals("TabFileUploadDirectory")){targetType = "TFUD";}//yrj 文件上传目录信息
			else if(className.equals("TabCodeDict")){targetType = "TBCD";}//yrj 埋码字典
			
			else if(className.equals("SiteManagement")){targetType = "STMM";}//yrj 站点管理

			else if(className.equals("TabUserInf")) {targetType = "USIF";}
			else if(className.equals("TabUserGroup")) {targetType = "USGP";}

			else if(className.equals("TabDepartment")) {targetType = "DEPA";}
			else if(className.equals("TabPermissions")) {targetType = "PERM";}
			else if(className.equals("TabRoles")||className.equals("TabReaularRole")) {targetType = "ROLE";}
			else if(className.equals("TabAction")){targetType = "ATON";}
			else if(className.equals("TabSysSource")){targetType = "SORC";}
			else if(className.equals("TabLoginLog")){targetType = "LLOG";}
			else if(className.equals("TabOperateLog")){targetType = "OLOG";}
			else if(className.equals("KeyWord")) {targetType = "KEYW";}
			else if(className.equals("SensitiveWord")) {targetType = "SENS";}
			else if(className.equals("Channel")) {targetType = "CHAL";}
			else if(className.equals("Attribute")) {targetType = "ATTB";}
			else if(className.equals("Topic")) {targetType = "TOPC";}
			else if(className.equals("TopicKeep")) {targetType = "TOPK";}
			else if(className.equals("Page")) {targetType = "PAGE";}
			else if(className.equals("TabPageVersion")) {targetType = "VERS";}  //页面版本
			else if(className.equals("DomainSort")) {targetType = "DMST";}
			else if(className.equals("Domain")) {targetType = "DMIN";}
			else if(className.equals("DomainRelationSort")) {targetType = "DMRS";}
			else if(className.equals("TabVideoMatchword")) {targetType = "MAWD";}
			else if(className.equals("TabMatchwordPageRelation")) {targetType = "MWPR";}
			else if(className.equals("TabMatchwordCatalogRelation")) {targetType = "MWCR";}
			else if(className.equals("TabVideoInterfaceLog")) {targetType = "VLOG";}
			else if(className.equals("TabVideoCopyrightinfo")) {targetType = "TCRI";}
			else if(className.equals("TabVideoKeyFrame")) {targetType = "TVKF";}
			else if(className.equals("TabVideoRelatedinfo")) {targetType = "TVRI";}
			else if(className.equals("TabVideoRelativevideo")) {targetType = "TVRV";}
			else if(className.equals("TabVideoReplayinfo")) {targetType = "TVRP";}
			else if(className.equals("TabVideoStream")) {targetType = "TVST";}
			else if(className.equals("TabVideoWordRelation")) {targetType = "TVWR";}
			
			else if(className.equals("DesignPage")) {targetType = "DEPA";}
			else if(className.equals("ElementTemplet")) {targetType = "ELTE";}
			else if(className.equals("DatablockTemplet")) {targetType = "DATE";}
			else if(className.equals("FrameTemplet")) {targetType = "FRTE";}
			else if(className.equals("CombinationTemplet")) {targetType = "COTE";}
			else if(className.equals("CombinationRelation")) {targetType = "CORE";}
			else if(className.equals("StyleCell")) {targetType = "STCE";}
			else if(className.equals("JS")) {targetType = "JSJS";}
			else if(className.equals("TagSet")) {targetType = "TGST";}
			else if(className.equals("TVChannel")) {targetType = "TVCL";}
			else if(className.equals("TVProgram")) {targetType = "TVPM";}
			
			else if(className.equals("TabFrame")) {targetType = "FRME";}
			else if(className.equals("TabElement")) {targetType = "ELMT";}
			else if(className.equals("TabSubdivision")) {targetType = "SUBD";}
			else if(className.equals("TabDatablock")) {targetType = "DATB";}
			else if(className.equals("TabCustomConfig")) {targetType = "CSTC";}
			
			
			else if(className.equals("TabTemporaryData")) {targetType = "TDAT";}
			else if(className.equals("TabTemporaryItem")) {targetType = "TITEM";}
			else if(className.equals("TabTemporaryRule")) {targetType = "TRUL";}
			//名人
			else if(className.equals("TabColumn")) {targetType = "COLUMN";}
			//栏目
			else if(className.equals("TabStars")) {targetType = "STARS";}
			//访谈
			else if(className.equals("TabVmsInterview")) {targetType = "TVIV";}
			//影视剧
			else if(className.equals("TabVmsTeleplay")) {targetType = "TVTY";}
			//工作量统计
			else if(className.equals("TabWorkloadStatistics")) {targetType = "TWLS";}
			
			//演员
			else if(className.equals("TabActor")) {targetType = "TACT";}
			//演员和视频关系
			else if(className.equals("TabActorVideoRelation")) {targetType = "TAVR";}
			//演员和图集关系
			else if(className.equals("TabActorPhotoAlbumRelation")) {targetType = "TAPR";}
			
			//视频接口临时数据
			else if(className.equals("TabVideoTemp")) {targetType = "TVTP";}
			else if(className.equals("TabCopyrightinfoTemp")) {targetType = "CRIT";}
			else if(className.equals("TabRelatedinfoTemp")) {targetType = "TRIT";}
			else if(className.equals("TabRelativevideoTemp")) {targetType = "TRVT";}
			else if(className.equals("TabReplayTemp")) {targetType = "TRPT";}
			else if(className.equals("TabVideoframeTemp")) {targetType = "TVFT";}
			else if(className.equals("TabVideostreamTemp")) {targetType = "TVSM";}
			else if(className.equals("TabVideoAlbumInfoTemp")) {targetType = "VAIT";}
			else if(className.equals("TabVideoAlbumRelationTemp")) {targetType = "VART";}
			else if(className.equals("TabVideoAlbumTemp")) {targetType = "TVAT";}
			
			else if(className.equals("TabTemplateIdType")) {targetType = "TIDT";}
			
			//终端
			else if(className.equals("Termination")) {targetType = "TERM";}
			else if(className.equals("Application")) {targetType = "APPL";}
			else if(className.equals("AppInterfaceRelation")) {targetType = "AIRN";}
			else if(className.equals("PageInfor")) {targetType = "PINF";}
			
			//文档
			else if(className.equals("DocumentType")) {targetType = "DOCT";}
			else if(className.equals("DoctTagsetRelation")) {targetType = "DTTR";}
			else if(className.equals("DatablockDocumentTypeRelation")) {targetType = "DDTR";}
			else if(className.equals("DocumentTypeField")) {targetType = "DTFD";}
			else if(className.equals("DocumentFieldConstraints")) {targetType = "DTFC";}
			else if(className.equals("Document")) {targetType = "DOMT";}
			else if(className.equals("DocumentPageRelations")) {targetType = "DTPR";}
			else if(className.equals("DocumentRelated")) {targetType = "DTRD";}
			else if(className.equals("DocumentHotWords")) {targetType = "DTHW";}
			
			//天脉
			else if(className.equals("TabTvTemp")) {targetType = "TMTV";}
			//日志
			else if(className.equals("TabTvTempLog")) {targetType = "TTTL";}
			//生活地图
			else if(className.equals("TabRestaurant")) {targetType = "TABR";}
			else if(className.equals("TabRestaurantCookbook")) {targetType = "TRCB";}
			else if(className.equals("TabRestaurantHomeBranch")) {targetType = "TRHB";}
			else if(className.equals("TabActivity")){targetType = "TAVY";}
			else if(className.equals("TabRestaurantActivity")){targetType="TARA";}
			else if(className.equals("TabLibraryCircle")){targetType="TLCC";}
			//评论员
			else if(className.startsWith("Commentator")){targetType="COMT";}
			//话题
			else if(className.startsWith("CommentTopic")){targetType="COMM";}
			//战报
			else if(className.equals("ScriptContent")){targetType="SCCO";}
			//抓取任务
			else if(className.equals("CatchTask")){targetType="CATC";}
			else if (className.equals("MessageTag")) {targetType="MSGT";}
			else {targetType = className;}
		}catch(Exception e) {
			targetType = null;
			throw new Exception(e);
		}
		
		return targetType;
	}
	
	public String getClass(String targetType) throws Exception {
		String className = "";
		
		try {
			if(targetType.equals("ARTI")) {	className = "TabArticle";}
			else  if(targetType.equals("ARTA")) {	className = "TabArticleAlbum";}
			else  if(targetType.equals("PHOT")) {	className = "TabPhoto";}
			else  if(targetType.equals("PHOA")) {	className = "TabPhotoAlbum";}
			else  if(targetType.equals("PHSD")) {	className = "TabPhotoSizeDetails";}			
			else  if(targetType.equals("VIDE")) {	className = "TabVideo";}
			else  if(targetType.equals("VIIN")) {	className = "TabVideoInfo";}
			else  if(targetType.equals("VIDA")) {	className = "TabVideoAlbum";}
			else  if(targetType.equals("VIDI")) {	className = "TabVideoAlbumInfo";}
			else  if(targetType.equals("VRRE")) {	className = "TabVideoRelated";}
			else  if(targetType.equals("VRHW")) {	className = "TabVideoHotWords";}
			else  if(targetType.equals("TCTC")) {	className = "TabCustomTopic";}

			else  if(targetType.equals("ARRE")) {	className = "TabArticleRelated";}
			else  if(targetType.equals("PARE")) {	className = "TabPhotoAlbumRelated";}
			else  if(targetType.equals("PARN")) {	className = "TabPhotoAlbumRelation";}
			else  if(targetType.equals("PVRN")) {	className = "TabPhotoAlbumRelation";}
			else  if(targetType.equals("ARHW")) {	className = "TabArticleHotWords";}
			
			else  if(targetType.equals("PARS")) {	className = "TabPageArticleRelations";}
			else  if(targetType.equals("PPAR")) {	className = "TabPagePhotoAlbumRelations";}
			else  if(targetType.equals("PVAR")) {	className = "TabPageVideoAlbumRelations";}
			else  if(targetType.equals("PHAR")) {	className = "TabPagePhotoRelations";}
			else  if(targetType.equals("CATE")) {	className = "TabCatalogCategories";}//编目分类
			else  if(targetType.equals("CATC")) {	className = "TabCatalogClassRelations";}//编目分类关系

			else  if(targetType.equals("CATR")) {	className = "TabCatalogArticleRelations";}//正文与编目关系
			else  if(targetType.equals("CPAR")) {	className = "TabCatalogPhotoalbRelations";}//图集与编目关系
			else  if(targetType.equals("CPTR")) {	className = "TabCatalogPhotoRelations";}//图片与编目关系
			else  if(targetType.equals("CVAR")) {	className = "TabCatalogVideoalbRelations";}//视频集与编目关系
			else  if(targetType.equals("CVTR")) {	className = "TabCatalogVideoRelations";}//视频编目关系
			

			else  if(targetType.equals("TTSB")) {	className = "TimerTaskBean";}//yrj 定时任务
			else  if(targetType.equals("TRTN")) {	className = "TabRealTimeNotice";}//yrj 公告
			else  if(targetType.equals("TRTS")) {	className = "TabRealTimeSysmessage";}//yrj 系统消息
			else  if(targetType.equals("TBII")) {	className = "TabBatchIssueInfo";}//yrj 批量发布
			else  if(targetType.equals("TBIM")) {	className = "TabBatchIssueMain";}//yrj 批量发布
			else  if(targetType.equals("TFUI")) {	className = "TabFileUploadInfo";}//yrj 文件上传基本信息
			else  if(targetType.equals("TFUD")) {	className = "TabFileUploadDirectory";}//yrj 文件上传目录表
			else  if(targetType.equals("TBCD")) {	className = "TabCodeDict";}//yrj 埋码字典
			else  if(targetType.equals("STMM")) {	className = "SiteManagement";}//yrj 
			
			else  if(targetType.equals("CHAL")) {   className = "Channel";}
			else  if(targetType.equals("ATTB")) {	className = "Attribute";}
			else  if(targetType.equals("TOPC")) {	className = "Topic";}
			else  if(targetType.equals("TOPK")) {	className = "TopicKeep";}
			else  if(targetType.equals("DMST")) {	className = "DomainSort";}
			else  if(targetType.equals("DMIN")) {	className = "Domain";}
			else  if(targetType.equals("DMRS")) {	className = "DomainRelationSort";}
			else  if(targetType.equals("PAGE")) {	className = "Page";}
			else  if(targetType.equals("VERS")) {	className = "TabPageVersion";}  //页面版本
			
			
			else  if(targetType.equals("DEPA")) {	className = "DesignPage";}
			else  if(targetType.equals("ELTE")) {	className = "ElementTemplet";}
			else  if(targetType.equals("DATE")) {	className = "DatablockTemplet";}
			else  if(targetType.equals("FRTE")) {	className = "FrameTemplet";}
			else  if(targetType.equals("COTE")) {	className = "CombinationTemplet";}
			else  if(targetType.equals("CORE")) {	className = "CombinationRelation";}
			else  if(targetType.equals("STCE")) {	className = "StyleCell";}
			else  if(targetType.equals("JSJS")) {	className = "JS";}
			else  if(targetType.equals("TGST")) {	className = "TagSet";}
			else  if(targetType.equals("TVCL")) {	className = "TVChannel";}
			else  if(targetType.equals("TVPM")) {	className = "TVProgram";}
			
			else  if(targetType.equals("FRME")) {	className = "TabFrame";}
			else  if(targetType.equals("ELMT")) {	className = "TabElement";}
			else  if(targetType.equals("SUBD")) {	className = "TabSubdivision";}
			else  if(targetType.equals("DATB")) {	className = "TabDatablock";}
			else  if(targetType.equals("CSTC")) {	className = "TabCustomConfig";}
			
			else  if(targetType.equals("TIDT")) {	className = "TabTemplateIdType";}
			else if(className.equals("TERM")) {targetType = "Termination";}
			else if(className.equals("APPL")) {targetType = "Application";}
			else if(className.equals("AIRN")) {targetType = "AppInterfaceRelation";}
			else if(className.equals("PINF")) {targetType = "PageInfor";}
			
			else if(className.equals("TMTV")) {targetType = "TabTvTemp";}
			
			else if(className.equals("TABR")) {targetType = "TabRestaurant";}
			else if(className.equals("TRCB")) {targetType = "TabRestaurantCookbook";}
			else if(className.equals("TRHB")) {targetType = "TabRestaurantHomeBranch";}
			else if(className.equals("TAVY")) {targetType = "TabActivity";}
			else if(className.equals("TARA")) {targetType="TabRestaurantActivity";}
			else if(className.equals("TLCC")){targetType="TabLibraryCircle";}
			
			else {	className = targetType;	}
		}catch(Exception e) {
			className = null;
			throw new Exception(e);
		} 
		
		return className;
	}
	
	/**
	 * 获取新媒体内容ID
	 * @param id
	 * @return
	 */
	public String getNewMediaContentId(String id){
		String newMediaId = "";
		String type = id.substring(0,4);
		String timestampStr = id.substring(4,id.length()-3);
		String time = sdf.format(new Date(Long.parseLong(timestampStr)));
		String randomStr = "";
		
		synchronized (KeyGenerator.class){
			if (newRandomNum >= 999999) {
				newRandomNum = 1;
			} else {
				newRandomNum = newRandomNum + 1;
			}
			randomStr =  newRandomNum + "";
			int lackLen = 6 - randomStr.length();
			for(int i=0; i < lackLen ; i++){
				randomStr = "0" + randomStr;
			}
		}
		newMediaId = type + time + columnCode + systemCode + randomStr;
		return newMediaId;
	}
	
	public static void main(String[] args) {
		String id = "VIDE1494465388256814";
		KeyGenerator k = new KeyGenerator();
		System.out.println(k.getNewMediaContentId(id));
		
	}
}
