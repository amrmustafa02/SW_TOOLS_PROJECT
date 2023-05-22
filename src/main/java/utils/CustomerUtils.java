package utils;

import Jsons_present.SendOrderJson;
import com.redhat.model.Meal;
import org.hibernate.criterion.Order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class CustomerUtils {


    public static String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        Date date = new Date();
        return formatter.format(date);
    }

    public static int getTotalPrice(Set<Meal> meals, List<SendOrderJson> sendOrderJsons) {
        int sum = 0;

        for (SendOrderJson orderJson : sendOrderJsons) {
            boolean found = false;
            for (Meal meal : meals) {
                if (meal.getId() == orderJson.getMealId()) {
                    sum += (meal.getPrice() * orderJson.getCount());
                    found = true;
                }
            }
            if(!found){
                return -1;
            }
        }
        return sum;
    }

}
