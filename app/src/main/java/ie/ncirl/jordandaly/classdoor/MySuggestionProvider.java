package ie.ncirl.jordandaly.classdoor;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by jdaly on 20/04/2016.
 */
public class MySuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "ie.ncirl.jordandaly.classdoor.MySuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MySuggestionProvider() {
        super();
        setupSuggestions(AUTHORITY, MODE);
    }
}
