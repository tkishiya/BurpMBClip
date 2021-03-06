package burp;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * HttpResponseEncodingUtilTest.
 *
 * @author https://twitter.com/tkishiya
 */
public class HttpResponseEncodingUtilTest {

    // encoding names: http://www.iana.org/assignments/character-sets/character-sets.xhtml
    private final String[] encodingNames = "437,850,851,852,855,857,860,861,862,863,865,866,869,904,adobe-standard-encoding,adobe-symbol-encoding,ami-1251,ami1251,amiga-1251,amiga1251,ansi_x3.110-1983,ansi_x3.4-1968,ansi_x3.4-1986,arabic,arabic7,asmo-708,asmo_449,big5,big5-hkscs,bocu-1,brf,bs_4730,bs_viewdata,ca,ccsid00858,ccsid00924,ccsid01140,ccsid01141,ccsid01142,ccsid01143,ccsid01144,ccsid01145,ccsid01146,ccsid01147,ccsid01148,ccsid01149,cesu-8,chinese,cn,cp-ar,cp-gr,cp-is,cp00858,cp00924,cp01140,cp01141,cp01142,cp01143,cp01144,cp01145,cp01146,cp01147,cp01148,cp01149,cp037,cp038,cp1026,cp154,cp273,cp274,cp275,cp278,cp280,cp281,cp284,cp285,cp290,cp297,cp367,cp420,cp423,cp424,cp437,cp500,cp50220,cp51932,cp775,cp819,cp850,cp851,cp852,cp855,cp857,cp860,cp861,cp862,cp863,cp864,cp865,cp866,cp868,cp869,cp870,cp871,cp880,cp891,cp903,cp904,cp905,cp918,cp936,csa7-1,csa7-2,csa71,csa72,csa_t500-1983,csa_z243.4-1985-1,csa_z243.4-1985-2,csa_z243.4-1985-gr,csadobestandardencoding,csamiga1251,csascii,csbig5,csbig5hkscs,csbocu-1,csbocu1,csbrf,cscesu-8,cscesu8,cscp50220,cscp51932,csdecmcs,csdkus,csebcdicatdea,csebcdiccafr,csebcdicdkno,csebcdicdknoa,csebcdices,csebcdicesa,csebcdicess,csebcdicfise,csebcdicfisea,csebcdicfr,csebcdicit,csebcdicpt,csebcdicuk,csebcdicus,cseucfixwidjapanese,cseuckr,cseucpkdfmtjapanese,csgb18030,csgb2312,csgbk,cshalfwidthkatakana,cshpdesktop,cshplegal,cshpmath8,cshppifont,cshppsmath,cshproman8,csibbm904,csibm00858,csibm00924,csibm01140,csibm01141,csibm01142,csibm01143,csibm01144,csibm01145,csibm01146,csibm01147,csibm01148,csibm01149,csibm037,csibm038,csibm1026,csibm1047,csibm273,csibm274,csibm275,csibm277,csibm278,csibm280,csibm281,csibm284,csibm285,csibm290,csibm297,csibm420,csibm423,csibm424,csibm500,csibm851,csibm855,csibm857,csibm860,csibm861,csibm863,csibm864,csibm865,csibm866,csibm868,csibm869,csibm870,csibm871,csibm880,csibm891,csibm903,csibm905,csibm918,csibmebcdicatde,csibmsymbols,csibmthai,csinvariant,csiso102t617bit,csiso10367box,csiso103t618bit,csiso10646utf1,csiso10swedish,csiso111ecmacyrillic,csiso115481,csiso11swedishfornames,csiso121canadian1,csiso122canadian2,csiso123csaz24341985gr,csiso128t101g2,csiso139csn369103,csiso13jisc6220jp,csiso141jusib1002,csiso143iecp271,csiso146serbian,csiso147macedonian,csiso14jisc6220ro,csiso150,csiso150greekccitt,csiso151cuba,csiso153gost1976874,csiso158lap,csiso159jisx02121990,csiso15italian,csiso16portuguese,csiso17spanish,csiso18greek7old,csiso19latingreek,csiso2022cn,csiso2022cnext,csiso2022jp,csiso2022jp2,csiso2022kr,csiso2033,csiso21german,csiso25french,csiso27latingreek1,csiso2intlrefversion,csiso42jisc62261978,csiso47bsviewdata,csiso49inis,csiso4unitedkingdom,csiso50inis8,csiso51iniscyrillic,csiso54271981,csiso5427cyrillic,csiso5428greek,csiso57gb1988,csiso58gb231280,csiso60danishnorwegian,csiso60norwegian1,csiso61norwegian2,csiso646basic1983,csiso646danish,csiso6937add,csiso69french,csiso70videotexsupp1,csiso84portuguese2,csiso85spanish2,csiso86hungarian,csiso87jisx0208,csiso885913,csiso885914,csiso885915,csiso885916,csiso88596e,csiso88596i,csiso88598e,csiso88598i,csiso8859supp,csiso88greek7,csiso89asmo449,csiso90,csiso91jisc62291984a,csiso92jisc62991984b,csiso93jis62291984badd,csiso94jis62291984hand,csiso95jis62291984handadd,csiso96jisc62291984kana,csiso99naplps,csisolatin1,csisolatin2,csisolatin3,csisolatin4,csisolatin5,csisolatin6,csisolatinarabic,csisolatincyrillic,csisolatingreek,csisolatinhebrew,csisotextcomm,csjisencoding,cskoi7switched,cskoi8r,cskoi8u,csksc56011987,csksc5636,cskz1048,csmacintosh,csmicrosoftpublishing,csmnem,csmnemonic,csn_369103,csnatsdano,csnatsdanoadd,csnatssefi,csnatssefiadd,csosdebcdicdf03irv,csosdebcdicdf041,csosdebcdicdf0415,cspc775baltic,cspc850multilingual,cspc862latinhebrew,cspc8codepage437,cspc8danishnorwegian,cspc8turkish,cspcp852,csptcp154,csscsu,csshiftjis,cstis620,cstscii,csucs4,csunicode,csunicode11,csunicode11utf7,csunicodeascii,csunicodeibm1261,csunicodeibm1264,csunicodeibm1265,csunicodeibm1268,csunicodeibm1276,csunicodejapanese,csunicodelatin1,csunknown8bit,csusdk,csutf16,csutf16be,csutf16le,csutf32,csutf32be,csutf32le,csutf7,csutf8,csventurainternational,csventuramath,csventuraus,csviqr,csviscii,cswindows1250,cswindows1251,cswindows1252,cswindows1253,cswindows1254,cswindows1255,cswindows1256,cswindows1257,cswindows1258,cswindows30latin1,cswindows31j,cswindows31latin1,cswindows31latin2,cswindows31latin5,cswindows874,cuba,cyrillic,cyrillic-asian,de,dec,dec-mcs,din_66003,dk,dk-us,ds2089,ds_2089,e13b,ebcdic-at-de,ebcdic-at-de-a,ebcdic-be,ebcdic-br,ebcdic-ca-fr,ebcdic-cp-ar1,ebcdic-cp-ar2,ebcdic-cp-be,ebcdic-cp-ca,ebcdic-cp-ch,ebcdic-cp-dk,ebcdic-cp-es,ebcdic-cp-fi,ebcdic-cp-fr,ebcdic-cp-gb,ebcdic-cp-gr,ebcdic-cp-he,ebcdic-cp-is,ebcdic-cp-it,ebcdic-cp-nl,ebcdic-cp-no,ebcdic-cp-roece,ebcdic-cp-se,ebcdic-cp-tr,ebcdic-cp-us,ebcdic-cp-wt,ebcdic-cp-yu,ebcdic-cyrillic,ebcdic-de-273+euro,ebcdic-dk-277+euro,ebcdic-dk-no,ebcdic-dk-no-a,ebcdic-es,ebcdic-es-284+euro,ebcdic-es-a,ebcdic-es-s,ebcdic-fi-278+euro,ebcdic-fi-se,ebcdic-fi-se-a,ebcdic-fr,ebcdic-fr-297+euro,ebcdic-gb-285+euro,ebcdic-int,ebcdic-international-500+euro,ebcdic-is-871+euro,ebcdic-it,ebcdic-it-280+euro,ebcdic-jp-e,ebcdic-jp-kana,ebcdic-latin9--euro,ebcdic-no-277+euro,ebcdic-pt,ebcdic-se-278+euro,ebcdic-uk,ebcdic-us,ebcdic-us-37+euro,ecma-114,ecma-118,ecma-cyrillic,elot_928,es,es2,euc-jp,euc-kr,extended_unix_code_fixed_width_for_japanese,extended_unix_code_packed_format_for_japanese,fi,fr,gb,gb18030,gb2312,gb_1988-80,gb_2312-80,gbk,gost_19768-74,greek,greek-ccitt,greek7,greek7-old,greek8,hebrew,hp-desktop,hp-legal,hp-math8,hp-pi-font,hp-roman8,hu,hz-gb-2312,ibm-1047,ibm-symbols,ibm-thai,ibm00858,ibm00924,ibm01140,ibm01141,ibm01142,ibm01143,ibm01144,ibm01145,ibm01146,ibm01147,ibm01148,ibm01149,ibm037,ibm038,ibm1026,ibm1047,ibm273,ibm274,ibm275,ibm277,ibm278,ibm280,ibm281,ibm284,ibm285,ibm290,ibm297,ibm367,ibm420,ibm423,ibm424,ibm437,ibm500,ibm775,ibm819,ibm850,ibm851,ibm852,ibm855,ibm857,ibm860,ibm861,ibm862,ibm863,ibm864,ibm865,ibm866,ibm868,ibm869,ibm870,ibm871,ibm880,ibm891,ibm903,ibm904,ibm905,ibm918,iec_p27-1,inis,inis-8,inis-cyrillic,invariant,irv,iso-10646,iso-10646-j-1,iso-10646-ucs-2,iso-10646-ucs-4,iso-10646-ucs-basic,iso-10646-unicode-latin1,iso-10646-utf-1,iso-11548-1,iso-2022-cn,iso-2022-cn-ext,iso-2022-jp,iso-2022-jp-2,iso-2022-kr,iso-8859-1,iso-8859-1-windows-3.0-latin-1,iso-8859-1-windows-3.1-latin-1,iso-8859-10,iso-8859-11,iso-8859-13,iso-8859-14,iso-8859-15,iso-8859-16,iso-8859-2,iso-8859-2-windows-latin-2,iso-8859-3,iso-8859-4,iso-8859-5,iso-8859-6,iso-8859-6-e,iso-8859-6-i,iso-8859-7,iso-8859-8,iso-8859-8-e,iso-8859-8-i,iso-8859-9,iso-8859-9-windows-latin-5,iso-celtic,iso-ir-10,iso-ir-100,iso-ir-101,iso-ir-102,iso-ir-103,iso-ir-109,iso-ir-11,iso-ir-110,iso-ir-111,iso-ir-121,iso-ir-122,iso-ir-123,iso-ir-126,iso-ir-127,iso-ir-128,iso-ir-13,iso-ir-138,iso-ir-139,iso-ir-14,iso-ir-141,iso-ir-142,iso-ir-143,iso-ir-144,iso-ir-146,iso-ir-147,iso-ir-148,iso-ir-149,iso-ir-15,iso-ir-150,iso-ir-151,iso-ir-152,iso-ir-153,iso-ir-154,iso-ir-155,iso-ir-157,iso-ir-158,iso-ir-159,iso-ir-16,iso-ir-17,iso-ir-18,iso-ir-19,iso-ir-199,iso-ir-2,iso-ir-21,iso-ir-226,iso-ir-25,iso-ir-27,iso-ir-37,iso-ir-4,iso-ir-42,iso-ir-47,iso-ir-49,iso-ir-50,iso-ir-51,iso-ir-54,iso-ir-55,iso-ir-57,iso-ir-58,iso-ir-6,iso-ir-60,iso-ir-61,iso-ir-69,iso-ir-70,iso-ir-8-1,iso-ir-8-2,iso-ir-84,iso-ir-85,iso-ir-86,iso-ir-87,iso-ir-88,iso-ir-89,iso-ir-9-1,iso-ir-9-2,iso-ir-90,iso-ir-91,iso-ir-92,iso-ir-93,iso-ir-94,iso-ir-95,iso-ir-96,iso-ir-98,iso-ir-99,iso-unicode-ibm-1261,iso-unicode-ibm-1264,iso-unicode-ibm-1265,iso-unicode-ibm-1268,iso-unicode-ibm-1276,iso5427cyrillic1981,iso646-ca,iso646-ca2,iso646-cn,iso646-cu,iso646-de,iso646-dk,iso646-es,iso646-es2,iso646-fi,iso646-fr,iso646-fr1,iso646-gb,iso646-hu,iso646-it,iso646-jp,iso646-jp-ocr-b,iso646-kr,iso646-no,iso646-no2,iso646-pt,iso646-pt2,iso646-se,iso646-se2,iso646-us,iso646-yu,iso_10367-box,iso_11548-1,iso_2033-1983,iso_5427,iso_5427:1981,iso_5428:1980,iso_646.basic:1983,iso_646.irv:1983,iso_646.irv:1991,iso_6937-2-25,iso_6937-2-add,iso_8859-1,iso_8859-10:1992,iso_8859-14,iso_8859-14:1998,iso_8859-15,iso_8859-16,iso_8859-16:2001,iso_8859-1:1987,iso_8859-2,iso_8859-2:1987,iso_8859-3,iso_8859-3:1988,iso_8859-4,iso_8859-4:1988,iso_8859-5,iso_8859-5:1988,iso_8859-6,iso_8859-6-e,iso_8859-6-i,iso_8859-6:1987,iso_8859-7,iso_8859-7:1987,iso_8859-8,iso_8859-8-e,iso_8859-8-i,iso_8859-8:1988,iso_8859-9,iso_8859-9:1989,iso_8859-supp,iso_9036,iso_tr_11548-1,it,jis_c6220-1969,jis_c6220-1969-jp,jis_c6220-1969-ro,jis_c6226-1978,jis_c6226-1983,jis_c6229-1984-a,jis_c6229-1984-b,jis_c6229-1984-b-add,jis_c6229-1984-hand,jis_c6229-1984-hand-add,jis_c6229-1984-kana,jis_encoding,jis_x0201,jis_x0208-1983,jis_x0212-1990,jp,jp-ocr-a,jp-ocr-b,jp-ocr-b-add,jp-ocr-hand,jp-ocr-hand-add,js,jus_i.b1.002,jus_i.b1.003-mac,jus_i.b1.003-serb,katakana,koi7-switched,koi8-e,koi8-r,koi8-u,korean,ks_c_5601-1987,ks_c_5601-1989,ksc5636,ksc_5601,kz-1048,l1,l10,l2,l3,l4,l5,l6,l8,lap,latin-9,latin-greek,latin-greek-1,latin-lap,latin1,latin1-2-5,latin10,latin2,latin3,latin4,latin5,latin6,latin8,mac,macedonian,macintosh,microsoft-publishing,mnem,mnemonic,ms936,ms_kanji,msz_7795.3,naplps,nats-dano,nats-dano-add,nats-sefi,nats-sefi-add,nc_nc00-10:81,nf_z_62-010,nf_z_62-010_(1973),no,no2,ns_4551-1,ns_4551-2,osd_ebcdic_df03_irv,osd_ebcdic_df04_1,osd_ebcdic_df04_15,pc-multilingual-850+euro,pc8-danish-norwegian,pc8-turkish,pt,pt154,pt2,ptcp154,r8,ref,rk1048,roman8,scsu,se,se2,sen_850200_b,sen_850200_c,serbian,shift_jis,st_sev_358-88,strk1048-2002,t.101-g2,t.61,t.61-7bit,t.61-8bit,tis-620,tscii,uk,unicode-1-1,unicode-1-1-utf-7,unknown-8bit,us,us-ascii,us-dk,utf-16,utf-16be,utf-16le,utf-32,utf-32be,utf-32le,utf-7,utf-8,ventura-international,ventura-math,ventura-us,videotex-suppl,viqr,viscii,windows-1250,windows-1251,windows-1252,windows-1253,windows-1254,windows-1255,windows-1256,windows-1257,windows-1258,windows-31j,windows-874,windows-936,x0201,x0201-7,x0208,x0212,yu".split(",");
    
    public HttpResponseEncodingUtilTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getEncoding method, of class HttpResponseEncodingUtil.
     */
    @Test
    public void testGetEncoding() {
        
        for (String encoding : encodingNames) {
            List<String> responseHeaders = new ArrayList<>();
            responseHeaders.add("HTTP/1.1 200 OK");
            responseHeaders.add(String.format("Content-Type: text/html;charset=%s", encoding));
            String responseBody = String.format("<html>\n<head>\n\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=%s\">\n</head>\n<body>", encoding);
            String result = HttpResponseEncodingUtil.getEncoding(responseHeaders, responseBody);
            String expResult = encoding;
            assertEquals(expResult, result);
        }
    }
    
    @Test
    public void testNullAndEmpty() {
        List<String> responseHeaders = null;
        String responseBody = "dummy";
        String result = HttpResponseEncodingUtil.getEncoding(responseHeaders, responseBody);
        String expResult = "";
        assertEquals(expResult, result);
        
        responseHeaders = new ArrayList<>();
        result = HttpResponseEncodingUtil.getEncoding(responseHeaders, responseBody);
        expResult = "";
        assertEquals(expResult, result);
        
        responseHeaders.add("");
        result = HttpResponseEncodingUtil.getEncoding(responseHeaders, responseBody);
        expResult = "";
        assertEquals(expResult, result);
        
        responseHeaders.add("Content-Type: text/html");
        result = HttpResponseEncodingUtil.getEncoding(responseHeaders, responseBody);
        expResult = "";
        assertEquals(expResult, result);
        
        responseHeaders = null;
        responseBody = null;
        result = HttpResponseEncodingUtil.getEncoding(responseHeaders, responseBody);
        expResult = "";
        assertEquals(expResult, result);
        
        responseBody = "";
        result = HttpResponseEncodingUtil.getEncoding(responseHeaders, responseBody);
        expResult = "";
        assertEquals(expResult, result);
    }
    
    @Test
    public void testHeader() {
        for (String encoding : encodingNames) {
            List<String> responseHeaders = new ArrayList<>();
            responseHeaders.add("HTTP/1.1 200 OK");
            responseHeaders.add(String.format("Content-Type: text/html;charset=%s", encoding));
            String responseBody = "<html>\n<head>\n\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=dummy\">\n</head>\n<body>";
            String result = HttpResponseEncodingUtil.getEncoding(responseHeaders, responseBody);
            String expResult = encoding;
            assertEquals(expResult, result);
            
            // latest statement wins
            responseHeaders.set(1, "Content-Type: text/html;charset=dummy");
            responseHeaders.add(String.format("Content-Type: text/html;charset=%s", encoding));
            result = HttpResponseEncodingUtil.getEncoding(responseHeaders, responseBody);
            assertEquals(expResult, result);
        }
    }
    
    @Test
    public void testBodyHtml() {
        for (String encoding : encodingNames) {
            List<String> responseHeaders = new ArrayList<>();
            responseHeaders.add("HTTP/1.1 200 OK");
            responseHeaders.add("Content-Type: text/html");
            String responseBody = String.format("<html>\n<head>\n\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=%s\">\n</head>\n<body>", encoding);
            String result = HttpResponseEncodingUtil.getEncoding(responseHeaders, responseBody);
            String expResult = encoding;
            assertEquals(expResult, result);
            
            // first statement wins
            responseBody = String.format("<html>\n<head>\n\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=%s\">\n", encoding);
            responseBody += "<meta charset=\"dummy\">\n</head>\n<body>";
            result = HttpResponseEncodingUtil.getEncoding(responseHeaders, responseBody);
            assertEquals(expResult, result);
        }
    }
    
    @Test
    public void testBodyHtml5() {
        for (String encoding : encodingNames) {
            List<String> responseHeaders = new ArrayList<>();
            responseHeaders.add("HTTP/1.1 200 OK");
            responseHeaders.add("Content-Type: text/html");
            String responseBody = String.format("<html>\n<head>\n\t<meta charset=\"%s\">\n</head>\n<body>", encoding);
            String result = HttpResponseEncodingUtil.getEncoding(responseHeaders, responseBody);
            String expResult = encoding;
            assertEquals(expResult, result);
        }
    }
    
    @Test
    public void testBodyXml() {
        for (String encoding : encodingNames) {
            List<String> responseHeaders = new ArrayList<>();
            responseHeaders.add("HTTP/1.1 200 OK");
            responseHeaders.add("Content-Type: text/html");
            String responseBody = String.format("<?xml encoding=\"%s\"?>\n<elm>", encoding);
            String result = HttpResponseEncodingUtil.getEncoding(responseHeaders, responseBody);
            String expResult = encoding;
            assertEquals(expResult, result);
        }
    }
}
