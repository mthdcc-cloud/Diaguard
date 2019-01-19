package com.faltenreich.diaguard.data.entity;

import androidx.annotation.StringRes;
import android.util.Log;

import com.faltenreich.diaguard.DiaguardApplication;
import com.faltenreich.diaguard.R;
import com.faltenreich.diaguard.data.Backupable;
import com.faltenreich.diaguard.data.PreferenceHelper;
import com.faltenreich.diaguard.util.Helper;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import static com.faltenreich.diaguard.data.entity.Food.Column.FAT_SATURATED;
import static com.faltenreich.diaguard.data.entity.Food.Column.PROTEINS;
import static com.faltenreich.diaguard.data.entity.Food.Column.SUGAR;

public class Food extends BaseServerEntity implements Backupable {

    public static final String BACKUP_KEY = "food";

    private static final String IMAGE_SUFFIX = ".jpg";
    private static final String KEYWORD_FULL_RESOLUTION = "full";

    @SuppressWarnings("WeakerAccess")
    public class Column extends BaseServerEntity.Column {
        public static final String NAME = "name";
        public static final String IMAGE_URL = "imageUrl";
        public static final String BRAND = "brand";
        public static final String INGREDIENTS = "ingredients";
        public static final String LABELS = "labels";
        public static final String CARBOHYDRATES = "carbohydrates";
        public static final String ENERGY = "energy";
        public static final String FAT = "fat";
        public static final String FAT_SATURATED = "fatSaturated";
        public static final String FIBER = "fiber";
        public static final String PROTEINS = "proteins";
        public static final String SALT = "salt";
        public static final String SODIUM = "sodium";
        public static final String SUGAR = "sugar";
        public static final String LANGUAGE_CODE = "languageCode";
        public static final String FOOD_EATEN = "foodEaten";
    }

    public enum Nutrient {
        CARBOHYDRATES(R.string.carbohydrates),
        SUGAR(R.string.sugar),
        ENERGY(R.string.energy),
        FAT(R.string.fat),
        FAT_SATURATED(R.string.fat_saturated),
        FIBER(R.string.fiber),
        PROTEINS(R.string.proteins),
        SALT(R.string.salt),
        SODIUM(R.string.sodium);

        private int textResId;

        Nutrient(@StringRes int textResId) {
            this.textResId = textResId;
        }

        public String getLabel() {
            return DiaguardApplication.getContext().getString(textResId);
        }

        public @StringRes int getUnit() {
            switch (this) {
                case ENERGY: return R.string.energy_acronym;
                default: return R.string.grams_acronym;
            }
        }

        public Float getValue(Food food) {
            switch (this) {
                case CARBOHYDRATES: return food.getCarbohydrates();
                case ENERGY: return food.getEnergy();
                case FAT: return food.getFat();
                case FAT_SATURATED: return food.getFatSaturated();
                case FIBER: return food.getFiber();
                case PROTEINS: return food.getProteins();
                case SALT: return food.getSalt();
                case SODIUM: return food.getSodium();
                case SUGAR: return food.getSugar();
                default: return null;
            }
        }

        public void applyValue(Food food, float value) {
            switch (this) {
                case CARBOHYDRATES: food.setCarbohydrates(value); break;
                case ENERGY: food.setEnergy(value); break;
                case FAT: food.setFat(value); break;
                case FAT_SATURATED: food.setFatSaturated(value); break;
                case FIBER: food.setFiber(value); break;
                case PROTEINS: food.setProteins(value); break;
                case SALT: food.setSalt(value); break;
                case SODIUM: food.setSodium(value); break;
                case SUGAR: food.setSugar(value); break;
                default: Log.e(Nutrient.class.getSimpleName(), "Unsupported nutrient for applyValue(): " + this);
            }
        }
    }

    @DatabaseField(columnName = Column.NAME)
    private String name;

    @DatabaseField(columnName = Column.IMAGE_URL)
    private String imageUrl;

    @DatabaseField(columnName = Column.BRAND)
    private String brand;

    @DatabaseField(columnName = Column.INGREDIENTS)
    private String ingredients;

    @DatabaseField(columnName = Column.LABELS)
    private String labels;

    @DatabaseField(columnName = Column.CARBOHYDRATES, defaultValue = "-1")
    private Float carbohydrates;

    @DatabaseField(columnName = Column.ENERGY, defaultValue = "-1")
    private Float energy;

    @DatabaseField(columnName = Column.FAT, defaultValue = "-1")
    private Float fat;

    @DatabaseField(columnName = FAT_SATURATED, defaultValue = "-1")
    private Float fatSaturated;

    @DatabaseField(columnName = Column.FIBER, defaultValue = "-1")
    private Float fiber;

    @DatabaseField(columnName = PROTEINS, defaultValue = "-1")
    private Float proteins;

    @DatabaseField(columnName = Column.SALT, defaultValue = "-1")
    private Float salt;

    @DatabaseField(columnName = Column.SODIUM, defaultValue = "-1")
    private Float sodium;

    @DatabaseField(columnName = SUGAR, defaultValue = "-1")
    private Float sugar;

    @DatabaseField(columnName = Column.LANGUAGE_CODE)
    private String languageCode;

    @ForeignCollectionField(columnName = Column.FOOD_EATEN)
    private ForeignCollection<FoodEaten> foodEaten;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getFullImageUrl() {
        if (imageUrl != null && imageUrl.contains("openfoodfacts") && imageUrl.endsWith(IMAGE_SUFFIX)) {
            String fullImageUrl = imageUrl.substring(0, imageUrl.length() - IMAGE_SUFFIX.length());
            int indexOfLastDot = fullImageUrl.lastIndexOf('.');
            fullImageUrl = fullImageUrl.substring(0, indexOfLastDot + 1);
            fullImageUrl = fullImageUrl + KEYWORD_FULL_RESOLUTION + IMAGE_SUFFIX;
            return fullImageUrl;
        } else {
            return imageUrl;
        }
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public Float getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(Float carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public Float getEnergy() {
        return energy;
    }

    public void setEnergy(Float energy) {
        this.energy = energy;
    }

    public Float getFat() {
        return fat;
    }

    public void setFat(Float fat) {
        this.fat = fat;
    }

    public Float getFatSaturated() {
        return fatSaturated;
    }

    public void setFatSaturated(Float fatSaturated) {
        this.fatSaturated = fatSaturated;
    }

    public Float getFiber() {
        return fiber;
    }

    public void setFiber(Float fiber) {
        this.fiber = fiber;
    }

    public Float getProteins() {
        return proteins;
    }

    public void setProteins(Float proteins) {
        this.proteins = proteins;
    }

    public Float getSalt() {
        return salt;
    }

    public void setSalt(Float salt) {
        this.salt = salt;
    }

    public Float getSodium() {
        return sodium;
    }

    public void setSodium(Float sodium) {
        this.sodium = sodium;
    }

    public Float getSugar() {
        return sugar;
    }

    public void setSugar(Float sugar) {
        this.sugar = sugar;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public ForeignCollection<FoodEaten> getFoodEaten() {
        return foodEaten;
    }

    public void setFoodEaten(ForeignCollection<FoodEaten> foodEaten) {
        this.foodEaten = foodEaten;
    }

    public String getValueForUi() {
        if (carbohydrates != null) {
            float valueFormatted = PreferenceHelper.getInstance().formatDefaultToCustomUnit(Measurement.Category.MEAL, carbohydrates);
            return Helper.parseFloat(valueFormatted);
        } else {
            return "";
        }
    }

    @Override
    public String getKeyForBackup() {
        return BACKUP_KEY;
    }

    @Override
    public String[] getValuesForBackup() {
        return new String[]{name, brand, ingredients, Float.toString(carbohydrates)};
    }

    @Override
    public String toString() {
        return name;
    }
}
