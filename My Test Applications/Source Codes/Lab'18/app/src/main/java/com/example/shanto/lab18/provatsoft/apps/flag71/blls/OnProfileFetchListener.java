package com.example.shanto.lab18.provatsoft.apps.flag71.blls;

import com.provatsoft.apps.flag71.models.UserProfile;
import com.provatsoft.apps.flag71.utils.OnErrorListener;

public interface OnProfileFetchListener extends OnErrorListener {
    void didFetch(UserProfile userProfile);
}
