package com.faltenreich.diaguard.feature.entry.edit.measurement.meal;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.faltenreich.diaguard.R;
import com.faltenreich.diaguard.feature.entry.edit.EntryEditFragment;
import com.faltenreich.diaguard.feature.entry.edit.measurement.EntryEditMeasurementTestUtils;
import com.faltenreich.diaguard.shared.data.database.entity.Category;
import com.faltenreich.diaguard.test.espresso.matcher.EditTextMatcher;
import com.faltenreich.diaguard.test.junit.rule.ApplyAppTheme;
import com.faltenreich.diaguard.test.junit.rule.CleanUpData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.robolectric.annotation.LooperMode;

@RunWith(AndroidJUnit4.class)
@LooperMode(LooperMode.Mode.PAUSED)
public class EntryEditMeasurementMealTest {

    @Rule public final ApplyAppTheme applyAppTheme = new ApplyAppTheme();
    @Rule public final TestRule dataCleanUp = new CleanUpData();

    @Before
    public void setup() {
        FragmentScenario.launchInContainer(EntryEditFragment.class, EntryEditMeasurementTestUtils.createBundle(Category.MEAL));
    }

    @Test
    public void confirmingEmptyValue_shouldShowWarning() {
        Espresso.onView(ViewMatchers.withId(R.id.fab_menu))
            .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.edit_text))
            .check(ViewAssertions.matches(EditTextMatcher.hasErrorText(R.string.validator_value_empty)));
    }

    @Test
    public void confirmingValueBelowMinimum_shouldShowWarning() {
        Espresso.onView(ViewMatchers.withId(R.id.edit_text))
            .perform(ViewActions.replaceText("0"));
        Espresso.onView(ViewMatchers.withId(R.id.fab_menu))
            .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.edit_text))
            .check(ViewAssertions.matches(EditTextMatcher.hasErrorText(R.string.validator_value_unrealistic)));
    }

    @Test
    public void confirmingValueAboveMaximum_shouldShowWarning() {
        Espresso.onView(ViewMatchers.withId(R.id.edit_text))
            .perform(ViewActions.replaceText("1001"));
        Espresso.onView(ViewMatchers.withId(R.id.fab_menu))
            .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.edit_text))
            .check(ViewAssertions.matches(EditTextMatcher.hasErrorText(R.string.validator_value_unrealistic)));
    }

    @Test
    public void confirmingValidValue_shouldSucceed() {
        Espresso.onView(ViewMatchers.withId(R.id.edit_text))
            .perform(ViewActions.replaceText("10"));
        Espresso.onView(ViewMatchers.withId(R.id.fab_menu))
            .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.edit_text))
            .check(ViewAssertions.matches(EditTextMatcher.hasNoErrorText()));
    }
}
