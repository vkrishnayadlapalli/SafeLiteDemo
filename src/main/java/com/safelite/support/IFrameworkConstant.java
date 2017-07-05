package com.safelite.support;

import com.safelite.report.ConfigFileReadWrite;

public interface IFrameworkConstant {

	String FILE_FRAMEWORK = "resources/framework.properties";
	String LOCATION_DATATABLE_EXCEL = ConfigFileReadWrite.read(FILE_FRAMEWORK, "LOCATION_DATATABLE_EXCEL");
	String SELECT_LOCATION = ConfigFileReadWrite.read(FILE_FRAMEWORK, "SELECT_LOCATION");

	public final class DIRECT_PARENT_CATEGORY {
		public static final String TIRES = "Tires";
		public static  final String CUSTOM ="Custom";
		public static  final String WHEELS ="Wheels";
		public static  final String TUBES_FLAPS ="Tubes & Flaps";
		public static  final String TUBES ="Tubes";
		public static  final String SUPPLIERS ="Supplies";
		public static final String HOME="Home";
		public static  final String QUOTE ="Quote";
		private DIRECT_PARENT_CATEGORY() {
		}
	}
	public final class TIRES_SUBCATEGORY {
		public static final String EARTH_MOVERS = "Earth Mover";
		public static final String FARM_TIRES = "Farm Tires";
		public static final String GOLF_ATV = "Golf & ATV";
		public static final String INDUSTRIAL_TIRES = "Industrial Tires";
		public static final String LAWN_GARDEN = "Lawn & Garden";
		public static final String MEDIUM_TRUCK = "Medium Truck";
		public static final String PASSENGER_LIGHT_TRUCK = "Passenger & Light Truck";
		public static final String TRAILER_TIRES = "Trailer Tires";

		private TIRES_SUBCATEGORY() {
		}
	}

	public final class WHEELS_SUBCATEGORY {
		public static final String STEEL_WHEEL = "Steel Wheel";
		public static final String AG_IMPLEMENTS = "Ag Implements";
		public static final String AGRICULTURE = "Agriculture";
		public static final String RECREATIONAL = "ATV/Recreational";
		public static final String CUSTOM = "Custom";
		public static final String TRAILER_ASSEMBLIES = "Trailer Assemblies";
		public static final String WHEEL_ACCESSORIES = "Wheel Accessories";
		public static final String WHEEL_STYLE="401S Marathon";
		public static final String WHEEL_STYLE1="401S Marathon";
		public static final String WHEEL_STYLE2="402S Alpine";
		public static final String WHEEL_STYLE3="403S Slalom";

		private WHEELS_SUBCATEGORY() {
		}
	}

	public final class TIRE {
		public static final String TIRE_SIZE = "ST185/80R13/6";
		public static  final String TIRE_SIZE_WITH_SPECIAL_CHARACTER="30.5L-32/14";
		public static final String TIRE_STYLE="UltraGrip 8 Performance";
		public static final String ALTERNATE_FARM_TIRE_SIZE ="26X9.00R12 6";
		public static final  String FARM_TIRE = "26X9.00R12/6";
		private TIRE() {
		}
	}

	public final class GLOBAL_SEARCH_KEYWORDS {
		public static final String GLOBAL_PRODUCT_SEARCH = "P215*";
		public static  final String GLOBAL_SEARCH_WHEELS_BRAND ="Advanti Racing";
		public static final String GLOBAL_SEARCH_INVALID_DATA="invalid";
		public static final String GLOBAL_SEARCH_MARKETING_DESCRIPTION="Assurance All-Season";
		public static final String GLOBAL_SEARCH_PRODUCT_NUMBER="117761373";
		public static final String GLOBAL_SERACH_PRODUCT_NUMBER1="1011002";
		public static final String GLOBAL_SERACH_PRODUCT_NUMBER_SINCERA_TOURING_SN211="28204538";
		public static final String GLOBAL_SEARCH_PRODUCT_NUMBER_EAGLE="166252528";
		public static final String GLOBAL_SEARCH_PRODUCT_NUMBER_WRANGLER="795698918";
		public static final String GLOBAL_SEARCH_PRODUCT_NUMBER_ASSUARANCE_ALL_SEASON1="407478374";
		public static final String GLOBAL_SEARCH_PRODUCT_NUMBER_ASSUARANCE_ALL_SEASON2="407212374";
		public static final String COMPARE_SEARCH_SUPPLIER_NUMBER="407476374";
		public static final String GLOBAL_SERACH_QUOTE_PRODUCT_NUMBER="320050";
		public static final String GLOBAL_SERACH_PRODUCT_NUMBER2="01613";
		public static final String GLOBAL_SEARCH_PRODUCT_SIZE ="15X7";
		public static final String GLOBAL_SEARCH_SHORT_DESCRIPTION="Advantage";
		public static final String GLOBAL_SEARCH_Z_NUMBER="107000713";
		public static final String GLOBAL_SEARCH_CUSTOM_WHEELS ="Custom Wheel";
		public static final String GLOBAL_SEARCH_PRODUCT_NUMBER_MRX_PLUS="01613";
		public static final String GLOBAL_SEARCH_PRODUCT_NUMBER_MR_TOURING="39875";
		public static final String GLOBAL_SEARCH_SUPPLIER_PRODUCT_NUMBER="17-6568RS";

		private GLOBAL_SEARCH_KEYWORDS() {
		}
	}

	/*Facet filter Headings*/
	public final class FACET_HEADING {
		public static final String KEYWORD = "Keyword";
		public static final String REFINE_BY = "Refine By";
		public static final String VEHICLE = "Vehicle";
		public static final String PRODUCT_TYPE = "Product Type";
		public static final String VIN = "VIN";
		public static final String LICENSE_PLATE ="License Plate";
		public static final String MY_LISTS="MY LISTS";
		private FACET_HEADING() {
		}
	}

	/*Refine By headers*/
	public final class REFINEBY_HEADERS {
		public static final String AVAILABILITY_FILTER_HEADER = "Availability";
		public static final String BRAND_FILTER_HEADER = "Brand";
		public static final String My_BRAND_FILTER_HEADER = "My Brands";
		public static final String BRANDS_FILTER_HEADER = "Brands";
		public static final String STYLE_FILTER_HEADER = "Style";
		public static final String SPEED_RATING_FILTER_HEADER = "Speed Rating";
		public static final String PLY_FILTER_HEADER = "Ply";
		public static final String RIM_DIAMETER_FILTER_HEADER = "Rim Diameter";
		public static final String RUN_FLAT_FILTER_HEADER = "Run Flat";
		public static final String SEASONAL_DESIGNATION_FILTER_HEADER = "Seasonal Designation";
		public static final String US_MILEAGE_WARRANTY_FILTER_HEADER = "US Mileage Warranty";
		public static final String RETAIL_PRICE_FILTER_HEADER = "Retail Price";
		public static final String DIAMETER = "Diameter";
		public static final String LUGS = "Lugs";
		public static final String WHEELS_BOLTS_CIRCLE = "Wheels Bolt Circle";
		public static final String FINISH="Finish";
		public static final String OFFSET="Offset";
		public static final String CLEAR_ALL ="Clear All";
		public static final String WEIGHT_COLOR="Weight Color";
		private REFINEBY_HEADERS() {
		}
	}

	/*RefineBy filter options*/
	public final class AVAILABILITY {
		public static final String AVAILABILITY_LOCAL = "Local";
		public static final String AVAILABILITY_LOCAL_PLUS = "Local+";
		public static final String AVAILABILITY_NATIONAL = "National";
		public static final String AVAILABILITY_ALL = "All";
		private AVAILABILITY() {
		}
	}

	/*RefineBy Brand labels*/
	public final class BRAND {
		public static final String BFGOODRICH = "BFGoodrich";
		public static final String BRIDGESTONE = "Bridgestone";
		public static final String CONTINENTAL = "Continental";
		public static final String COOPER = "Cooper";
		public static final String DUNLOP = "Dunlop";
		public static final String FALKEN = "Falken";
		public static final String FEDERAL = "Federal";
		public static final String FIRESTONE = "Firestone";
		public static final String GOODYEAR = "Goodyear";
		public static final String HANKOOK = "Hankook";
		public static final String HERCULES = "Hercules";
		public static final String KELLY = "Kelly";
		public static final String KUMHO = "Kumho";
		public static final String MICHELIN = "Michelin";
		public static final String NEXEN = "Nexen";
		public static final String PIRELLI = "Pirelli";
		public static final String UNIROYAL = "Uniroyal";
		public static final String YOKOHAMA = "Yokohama";
		public static final String ADVANTI_RACING ="Advanti Racing";
		public static final String ALCOA= "Alcoa";
		public static final String BADA="BADA";
		public static final String CARLISLE ="Carlisle";
		public static final String BLACK_ROCK ="Black Rock";
		public static final String PACER="Pacer";
		private BRAND() {
		}
	}

	public final class WHEELBOLTCIRCLE {
		public static final String SIZE_5 = "5X135";
		public static final String TIRE_SIZE_50 = "5X4.50";
		public static final String TIRE_SIZE_225 ="10X225";
		public static final String TIRESIZE_225 ="225";
		private WHEELBOLTCIRCLE() {

		}
	}

	public final class LUGS {
		public static final String SIZE_5 = "5";
		private LUGS() {
		}
	}

	/*Availability styles labels*/
	public final class STYLES {
		public static final String GFORCE = "g-Force T/A KDWS";
		public static final String LONGTRAIL = "Long Trail T/A Tour";
		public static final String COBRA_RADIAL="Cobra Radial G/T";
		public static final String CONTI_PRO_CONTACT="ContiProContact";
		public static final String TRENDSETTER="Trendsetter SE";
		public static final String RADAIL_TA="Radial T/A";
		public static final String TRACKTION_61GB_LINK=" 61GB Traktion";
		public static final String TRACKTION_61GB="61GB Traktion";
		public static final String TARKIN_61GBMS ="61MS Traktion";
		public static final String GLR02_RADIAL="GLR02 - Radial OTR";
		public static final String ALL_WEATHER_R_3="All Weather R-3";
		public static final String WHEEL="Wheel";
		public static final String AFFINITY_TOURING="Affinity Touring";
		public static final String IMOVE_GEN2="iMOVE GEN2 AS";
		public static final String TOURING ="Touring";
		public static final String ALL_TERRAIN_KO2 ="All-Terrain T/A KO2";
		public static final String BSOFT ="997B Soft 8";
		public static final String TRANSFORCE_HT="Transforce HT";
		public static final String TRANSFORCE_AT="Transforce AT";
		public static final String TURANZA="Turanza EL400 02 RFT";
		public static final String SINCERA_TOURING="Sincera Touring SN211";
		public static final String CHROME_SMOOTHIE ="03C Chrome Smoothie";
		public static final String STUD="Stud";
		public static final String PD860="PD860";
		public static final String I_109_AP_RIB="I-109 A/P Rib";
		public static final String MRX_PLUS="MRX Plus IV";
		private STYLES() {
		}
	}
	/*RefineBy styles labels*/
	public final class OFFSET {
		public static final String SIZE_25 = "+25";
		public static final String SIZE_00 = "+00";
		public static final String SIZE_06 = "+06";
		private OFFSET() {
		}
	}
	public final class DIAMETER {
		public static final String SIZE_15 = "15";
		public static final String SIZE_16 = "16";
		public static final String SIZE_19 = "19.5";
		private DIAMETER() {
		}
	}
	/*RefineBy styles labels*/
	public final class TIRE_SIZE {
		public static final String FRONT_SIZE = "245/40ZR20XL";
		public static final String TIRE_SIZE ="13.00-24/12";
		public static final String TIRE_SIZE_RESULTS ="13.00-24/1";
		public static final String PARTIAL_TIRE_SIZE ="13.00-24/1*";
		public static final String REAR_SIZE = "275/35ZR20XL";
		public static final String INVALID_FRONT_SIZE="asdf";
		public static final String INVALID_REAR_SIZE="dfsg";
		public static final String INVALID_TIRE_SIZE_DATA="inv";
		public static final String PARTIAL_TIRE_SIZE_DATA="40ZR20XL";

		private TIRE_SIZE() {
		}
	}
	/*RefineBy styles labels*/
	public final class RUN_FLAT {
		public static final String RUN_FLAT_LABEL = "N";
		private RUN_FLAT() {
		}
	}
	/*RefineBy finish labels*/
	public final class FINISH {
		public static final String BLACK = "Black";
		public static final String ANTHRACITE = "Anthracite";
		public static final String BRONZE = "Bronze";
		public static final String CHROME = "Chrome";
		public static final String GOLD = "Gold";
		public static final String MACHINED_FINISHED = "Machined Finished";
		public static final String POLISHED = "Polished";
		public static final String SILVER = "Silver";
		public static final String TWO_TONE = "Two-Tone";
		public static final String WHITE = "White";
		private FINISH() {
		}
	}
	/*Refine By weight color labels*/
	public static class WEIGHT_COLOR{
		public static final String BLACK = "BLACK";
		public static final String BLUE = "BLUE";
		public static final String GREEN = "GREEN";
		public static final String ORANGE = "ORANGE";
		public static final String PURPLE = "PURPLE";
		public static final String RED = "RED";
		public static final String SILVER = "SILVER";
		public static final String YELLOW = "YELLOW";
		private WEIGHT_COLOR() {
		}
	}

	public final class VIEW {
		public static final String GRID_VIEW = "gridView";
		public static final String LIST_VIEW = "listView";
		private VIEW() {
		}
	}
	public final class SPEED_RATING {
		public static final String T = "T";
		public static final String S = "S";
		public static final String H = "H";
		public static final String V = "V";
		public static final String W = "W";
		private SPEED_RATING() {
		}
	}
	public final class PRODUCT_TYPE {
		public static final String TIRES = "tires";
		public static final String WHEELS = "wheels";
		public static final String PRODUCT_CATEGORY_TIRES = "Tires";
		public static final String PRODUCT_CATEGORY_WHEELS = "Wheels";
        public static final String SUPPLIES ="supplies";
        public static final String TIRE="tire";
        public static final String TPMS="tpms";
        public static final String WIDTH="9.50";
        public static final String ASPECT_RATIO="30";
        public static final String DIAMETER="15";
		private  PRODUCT_TYPE() {
		}
	}
	/*RefineBy Seasonal designation labels*/
	public final class SEASONAL_DESIGNATION {
		public static final String ALL_OTHER = "All Other";
		public static final String WINTER = "Winter";
		private SEASONAL_DESIGNATION() {
		}
	}

	/*Account menu headers*/
	public final class ACCOUNT_MENU_HEADERS {
		public static final String MY_PROFILE = "My Profile";
		public static final String MESSAGES = "Messages";
		public static final String APPROVALS = "Approvals";
		public static final String STOCK_ORDERS = "Stock Order";
		public static final String LISTS = "Lists";
		public static final String USERS = "Users";
		public static final String ACCOUNTING = "Accounting";
		public static final String ORDER_HISTORY = "Order History";
		public static final String LOCATION_SETTINGS = "Location settings";
		public static final String QUOTE_SETUP = "Quote Setup";
		public static final String MY_QUOTE = "My Quote";
		public static final String HELP = "Help";
		public static final String LOGOFF = "Log Off";
		private ACCOUNT_MENU_HEADERS() {
		}
	}
	/*Product Specifications*/
	public final class SPECIFICATIONS {
		public static final String SUPPLIER_NUMBER = "Supplier #";
		public static final String UTQGL = "UTQGL";
		public static final String EXTENDED_MOBILITY = "Extended Mobility";
		public static final String MAX_LOAD_SINGLES = "Max Load Single";
		public static final String SEASON_DESIGNATION = "Season Designation";
		public static final String WARRANTY = "Warranty";
		public static final String PRODUCT_NUMBER = "Product #";
		public static final String LOAD_OR_SPEED = "Load/Speed";
		public static final String FORD_PART_NUMBER = "FORD PART NUMBER";
		public static final String OVERALL_DIAMETER = "Overall Diameter";
		public static final String SIDEWALL = "Sidewall";
		public static final String Z_NUMBER = "Z #";
		public static final String THREAD_DEPTH = "Tread Depth";
		public static final String MAX_LOAD_DUAL = "Max Load Dual";
		public static final String RIM_WIDTH_MIN = "Rim Width Min";
		public static final String STUDDABLE = "Studdable";
		public static final String BOLT_CIRCLE="Bolt Circle";
		public static final String DIAMETER="Diameter";
		public static final String OFFSET ="Offset";
		private SPECIFICATIONS() {
		}
	}

	public final class VEHICLE_SEARCH_KEYWORDS {
		public static final String YEAR = "2014";
		public static final String MAKE = "BMW";
		public static final String MODEL = "228i";
		public static final String TRIM = "Convertible";
		public static final String INVALID_YEAR="1980";
		public static final String INVALID_MAKE="Honda";
		public static final String INVALID_MODEL="Accord";
		public static final String INVALID_TRIM="Hatchback";
		public static final String YEAR1="2015";
		public static final String MAKE1="BMW";
		public static final String MODEL1="535i";
		public static final String TRIM1="GT Sedan";
		public static final String RESULT_PAGE_YEAR="1998";
		public static final String RESULT_PAGE_MAKE="Rolls Royce";
		public static final String RESULT_PAGE_MODEL="Park Ward";
		public static final String RESULT_PAGE_TRIM="Standard";
		private VEHICLE_SEARCH_KEYWORDS() {
		}
	}

	public final class VIN_SEARCH_KEYWORDS {
		public static final String VIN_NUMBER = "2T1BR32EX6C649036";
		private VIN_SEARCH_KEYWORDS() {
		}
	}
	public final class SELECT_OPTIONS {
		public static final String SELECT_YES = "Yes";
		public static final String SELECT_NO = "No";
		private SELECT_OPTIONS() {

		}
	}

	public final class CREATE_QUOTE {
		public static final String ALL_TIRES = "All tires";
		private CREATE_QUOTE() {
		}
	}


	public final class ADD_TAX_DETAILS {
		public static final String TAX_DESCRIPTION = "State and County Sales Tax";
		public static final String TAX_PERCENT = "7.0000%";
		public static final String TAX_CAPAMOUNT= "$10.00";
		private ADD_TAX_DETAILS() {
		}
	}

	public final class ADD_SERVICE_DETAILS {
		public static final String SERVICE_DESCRIPTION = "Tire Installation includes Mounting, Lifetime, Balancing and Valve Stem";
		public static final String QTY = "1";
		public static final String AMOUNT = "15.00";
		public static final String UPDATED_SERVICE_DESCRIPTION = "UpdatedTire Installation includes Mounting, Lifetime, Balancing and Valve Stem";
		public static final String UPDATED_QTY = "2";
		public static final String UPDATED_AMOUNT = "10.00";
		public static final String ADD_BUTTON= "Add";
		public static  final String SAVE_BUTTON ="Save";
		public static final String CANCEL_BUTTON ="Cancel";
		private ADD_SERVICE_DETAILS() {
		}
	}

	public final class ADD_FEES_DETAILS {
		public static final String FEES_DESCRIPTION = "Mandatory State Fee";
		public static final String UPDATED_FEES_DESCRIPTION = "Updated Mandatory State Fee";
		public static final String QTY = "1";
		public static final String UPDATED_QTY = "2";
		public static final String PERCENT = "2.0000";
		public static final String UPDATED_PERCENT = "1.0000";
		private ADD_FEES_DETAILS() {
		}
	}
	public final class LOCATION_SETTINGS_HEADINGS {
		public static final String DEFAULT_SOURCING = "Default sourcing during search to";
		public static final String DEFAULT_SORTING ="Default sorting of search results";
		public static final String SHOW_ONLY_PREFERRED_BRANDS="Show only preferred brands in search results";
		public static final String ENABLE_RETAIL_QUOTE ="Enable retail quotes";
		private LOCATION_SETTINGS_HEADINGS() {
		}
	}
	public final class FORM_EDIT_HEADINGS{
		public static final String PREFERRED_BRANDS_LABEL ="Preferred Brands";
		public static final String PRIVATE_BRANDS_LABEL ="Private Brands";
		public static final String FLAG_BRANDS_LABEL ="Flag Brands";
		public  static final String PREFERRED_BRANDS="preferred-brands-tab";
		public  static final String PRIVATE_BRANDS="private-brands-tab";
		public  static final String FLAG_BRANDS="flag-brands-tab";

	}
	public final class LOCATION_BRANDS{
		public static final String ACCELERA="Accelera";
		public static final String BFGOODRICH="BFGoodrich";
		public static final String BRIDGESTONE="Bridgestone";
		public static final String COOPER="Cooper";
		public static final String DUNLOP="Dunlop";
		private LOCATION_BRANDS(){
		}
	}

	public final class MORE_LESS {
		public static final String MORE = "More";
		public static final String LESS = "Less";
		private MORE_LESS() {
		}
	}
	public final class FRIEGHT_COST_SUBHEADING {
		public static final String PRODUCT = "PRODUCT";
		public static final String QTY = "QTY";
		public static final String SOURCED_QTY="Sourced Qty";
		public static final String EST_DELIVERY_DATE="EST. DELIVERY DATE";
		public static final String DELIVERY_OPTION="Delivery Option";
		public static final String FRIEGHT_BREADCRUMB="Freight";
		private FRIEGHT_COST_SUBHEADING() {
		}
	}
	public final class FRIEGHT_SELECTBOX{
		public static final String ONE_DAY_AIR = "One Day Air - $476.03";
		public static final String LOWER_COST = "Lowest Cost - $97.16";
		public static final String TWO_DAY_AIR="Two Day Air - $289.10";
	    private FRIEGHT_SELECTBOX(){
	}
	}

	/*Vehicle guide page headers like OE Wheel,OE size,etc*/

	public final class VEHICLE_GUIDE_HEADERS{
		public static final String OPTION = "Option";
		public static final String POS = "Pos";
		public static final String OE_SIZE="OE Size";
		public static final String OE_WHEEL="OE Wheel";
		public static final String WHEEL_PLUS="Wheel Plus";
		private VEHICLE_GUIDE_HEADERS(){
		}
	}
	/*Vehicle guide OE size and OE wheel values*/
	public final class VEHICLE_GUIDE_VALUES{
		public static final String OE_SIZE1 = "205/50R17 89H";
		public static final String OE_SIZE2 = "225/40R18 92V";
		public static final String OE_SIZE_GT_SEDAN1="245/50R18 100Y";
		public static final String OE_SIZE_GT_SEDAN2="245/50R18 100V";
		public static final String OE_SIZE_F="225/40R18 88Y";
		public static final String OE_SIZE_R="245/35R18 88Y";
		public static final String OE_WHEEL1="17x7";
		public static final String OE_WHEEL2="18x7.5";
		public static final String OE_WHEEL_F="18x7.5";
		public static final String OE_WHEEL_R="18x8";
		public static final String QTY_VERIFY="Package set of 4";
		public static final String TPMS_VALUE1="Service Pack";
		private VEHICLE_GUIDE_VALUES(){
		}
	}
	/*Options available in Action Dropdown*/

	public final class ACTION_DROPDOWN {
     public static  final String ADD_TO_LIST="Add to List";
		public static  final String CREATE_LIST="Create List";
		public static  final String ADD_TO_PACKAGE="Add to Package";
		public static  final String FRIEGHT_COST="Freight Cost";
		public static  final String QUOTE="Quote";
		public static final String CREATE_PACKAGE="Create Package";
		public static final String ADD_TO_CART="";
		public static final String QUANTITY= "4";
		ACTION_DROPDOWN(){
		}
	}
	/*List page Headers*/

	public final class LIST_PAGE_HEADERS{
		public static final String MY_LISTS="MY LISTS";
		public static final String CREATE_LISTS="CREATE LIST";
		public static final String ADD_TO_LIST="ADD TO LIST";
		public static final String LIST_NAME="List";
		public static final String SHARE_LIST_YES="Yes";
		public static final String SHARE_LIST_NO="No";
		public static final String RECURRING_ORDER_YES="Yes";
		public static final String RECURRING_ORDER_NO="No";
		public static final String LIST_BREADCRUMB="Lists";
		LIST_PAGE_HEADERS(){

		}
	}
	public final class PRODUCT_PROFILE {
		public static final String MINIMUM_COST_RANGE = "10";
		public static final String MAXIMUM_COST_RANGE = "200";
		public static final String GROSS_PROFIT = "1";
		public static final String MINIMUM_GROSS_PROFIT="2";
		public static final String GROUP = "Passenger Tire";
		public static final String BRAND = "Goodyear";
		public static final String BRAND1 = "BFGoodrich";
		public static final String BRAND2 = "Bridgestone";
		public static final String STYLE = "Assurance All-Season";
		public static final String FLAT_PRICE = "10";
		public static final String FLAT_PRICE1 = "20";
		private  PRODUCT_PROFILE() {
		}
	}
	public final class MARKUP_PERCENT {
		public static final String MARKUP_BY = "5";
		public static final String MINIMUM_MARKUP = "2";
		public static final String FIXED_CENTS ="0.50";
		public static final String ROUND_CENTS_NEAR_DOLOR = "0.00";
		public static final String AMOUNT_PER_QUOTE ="5";
		private  MARKUP_PERCENT() {
		}
	}

	public final class ADD_ITEM_TO_QUOTE {
		public static final String QTY = "1";
		public static final String CUSTOMERPO = "34567";
		public static final String COMMENTS = "purchasing the product";
	}

	public final class CUSTOMER_INFO {
		public static final String FIRSTNAME = "Divyak";
		public static final String LASTNAME = "Kumar";
		public static final String ADDRESS1 = "United states";
		public static final String CITY = "Newyork";
		public static final String STATE = "Texas";
		public static final String ZIPCODE = "502485";
		public static final String EMAIL = "dchiluveru@atd-us.com";
	}
	public final class CREATE_USER_INFO {
		public static final String NAME = "DIVYAK";
		public static final String EMAIL = "DChiluveru@atd-us.com";
		public static final String PHONE = "5246897562";
		public static final String FAX = "2586458693";
		public static final String USER_ID = "divyak123";
		public static final String PASSWORD = "divyak123";
		public static final String CONFIRM_PASSWORD = "divyak123";


	}
	public final class MY_BRANDS{
		public static final String ALL="All";
		public static final String PREFERRED="Preferred";
		public static final String PRIVATE="Private";
		public static final String FLAG="Flag";
	}
	public final class MY_PROFILE{
		public static final String DEFAULT_VIEW_lIST="listView";
		public static final String DEFAULT_VIEW_GRID="gridView";
		MY_PROFILE(){

		}
	}
	public final class VIEW_HEADERS{
		public static final String PRODUCT_NUMBER="Product #";
		public static final String WARRANTY="Warranty";
		public static final String COST="Cost";
		public static final String RETAIL="Retail";
		public static final String LOCAL_DC="Local DC";
		public static final String LOCAL_PLUS="Local+";
		public static final String NATIONAL="National";
		public static final String COMPARE="Compare";
		public static final String SUPPLIER_NUMBER="Supplier #";
		public static final String LIST_VIEW="grid-header row";
		public static final String GRID_VIEW="gridDiv";
		VIEW_HEADERS(){

		}
	}
}
