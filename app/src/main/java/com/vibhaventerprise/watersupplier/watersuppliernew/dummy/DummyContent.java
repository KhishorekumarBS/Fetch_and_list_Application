package com.vibhaventerprise.watersupplier.watersuppliernew.dummy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapters.AsyncResponse;
import adapters.DataFetch;


/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent implements AsyncResponse{

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static int COUNT = 0;
    static JSONArray JA;
    void set(DataFetch df)
    {
        df.delegate=this;
    }
    static {
        DataFetch df = new DataFetch();
        DummyContent dc = new DummyContent();
        dc.set(df);
        df.execute();
    }
    void populate(){
        COUNT=JA.length();

        for (int i = 1; i < JA.length(); i++) {
            JSONObject JO = null;
            try {
                JO = (JSONObject) JA.get(i);
                addItem(createDummyItem(JO.get("date").toString(),JO.get("number").toString()));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static DummyItem createDummyItem(String data, String number) {
        return new DummyItem(String.valueOf(1),data, makeDetails(number));
    }

    public static String makeDetails(String number) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(number);
        builder.append("\nMore details information here.");
        return builder.toString();
    }

    @Override
    public void processFinish(JSONArray JB) {
        JA=JB;
        populate();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String content;
        public final String details;

        public DummyItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
