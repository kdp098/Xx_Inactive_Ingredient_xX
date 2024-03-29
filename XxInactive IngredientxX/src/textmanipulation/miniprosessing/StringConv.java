package textmanipulation.miniprosessing;

/**
 * 半角、全角英数記号変換処理
 */
public class StringConv {
    // 半角←→全角変換テーブル
    private static String eisukigoHanZenTbl[][] =
        {
             {"!","！"},{"\"","”"},{"#","＃"},{"$","＄"},{"%","％"}
            ,{"&","＆"},{"'","’"},{"(","（"},{")","）"},{"*","＊"}
            ,{"+","＋"},{".","．"},{"-","−"},{".","．"},{"/","／"}
            ,{":","："},{";","；"},{">","＞"},{"=","＝"},{"<","＜"}
            ,{"?","？"},{"@","＠"},{"[","［"},{"\\","￥"},{"]","］"}
            ,{"^","＾"},{"_","＿"},{"`","‘"},{"{","｛"},{"|","｜"}
            ,{"}","｝"},{"~","〜"},{",","，"}
            ,{"1","１"},{"2","２"},{"3","３"},{"4","４"},{"5","５"}
            ,{"6","６"},{"7","７"},{"8","８"},{"9","９"},{"0","０"}
            ,{"A","Ａ"},{"B","Ｂ"},{"C","Ｃ"},{"D","Ｄ"},{"E","Ｅ"}
            ,{"F","Ｆ"},{"G","Ｇ"},{"H","Ｈ"},{"I","Ｉ"},{"J","Ｊ"}
            ,{"K","Ｋ"},{"L","Ｌ"},{"M","Ｍ"},{"N","Ｎ"},{"O","Ｏ"}
            ,{"P","Ｐ"},{"Q","Ｑ"},{"R","Ｒ"},{"S","Ｓ"},{"T","Ｔ"}
            ,{"U","Ｕ"},{"V","Ｖ"},{"W","Ｗ"},{"X","Ｘ"},{"Y","Ｙ"}
            ,{"Z","Ｚ"}
            ,{"a","ａ"},{"b","ｂ"},{"c","ｃ"},{"d","ｄ"},{"e","ｅ"}
            ,{"f","ｆ"},{"g","ｇ"},{"h","ｈ"},{"i","ｉ"},{"j","ｊ"}
            ,{"k","ｋ"},{"l","ｌ"},{"m","ｍ"},{"n","ｎ"},{"o","ｏ"}
            ,{"p","ｐ"},{"q","ｑ"},{"r","ｒ"},{"s","ｓ"},{"t","ｔ"}
            ,{"u","ｕ"},{"v","ｖ"},{"w","ｗ"},{"x","ｘ"},{"y","ｙ"}
            ,{"z","ｚ"}
        };
    /**
     * 半角英数記号を全角英数記号に変換する
     * @param   String str  文字列
     * @return  String 変換後の文字列
     */
    public static String eisukigoHanToZen(String str) {
        String zenstr = "";
        String chkstr = "";
        // strを1文字づつ eisukigouHanZenTblと照らし合わせて変換する
        for (int i = 0; i < str.length(); i++) {
            chkstr = str.substring(i, i+1);
            for (int j = 0; j < eisukigoHanZenTbl.length; j++) {
                if (chkstr.equals(eisukigoHanZenTbl[j][0])) {
                    chkstr = eisukigoHanZenTbl[j][1];
                    break;
                }
            }
            zenstr = zenstr + chkstr;
        }
        return zenstr;
    }

    /**
     * 全角英数記号を半角英数記号に変換する
     * @param   String str  文字列
     * @return  String 変換後の文字列
     */
    public static String eisukigoZenToHan(String str) {
        String hanstr = "";
        String chkstr = "";
        // strを1文字づつ eisukigouHanZenTblと照らし合わせて変換する
        for (int i = 0; i < str.length(); i++) {
            chkstr = str.substring(i, i+1);
            for (int j = 0; j < eisukigoHanZenTbl.length; j++) {
                if (chkstr.equals(eisukigoHanZenTbl[j][1])) {
                    chkstr = eisukigoHanZenTbl[j][0];
                    break;
                }
            }
            hanstr = hanstr + chkstr;
        }
        return hanstr;
    }
}

