package com.ttp.getapp.Service;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.ttp.getapp.Common.Common;
import com.ttp.getapp.Model.Token;

/**
 * Created by 0047TiTANplateform_ on 2018-03-22.
 */

public class MyFirebaseIdSerive extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        updateToServer(refreshedToken);
    }

    private void updateToServer(String refreshedToken) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference tokens = db.getReference("Tokens");
        Token data = new Token(refreshedToken, true);
        tokens.child(Common.currentuser.getPhone()).setValue(data);
    }
}
