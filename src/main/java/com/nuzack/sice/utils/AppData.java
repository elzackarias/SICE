package com.nuzack.sice.utils;

import com.nuzack.sice.models.UserData;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class AppData {
    private static AppData instance;
    private UserData userData;
    private final Preferences prefs;

    private static final String PREFS_NODE = "com_nuzack_sice_session";

    private AppData() {
        prefs = Preferences.userRoot().node(PREFS_NODE);
    }

    public static AppData getInstance() {
        if (instance == null) {
            instance = new AppData();
        }
        return instance;
    }

    public UserData getUserData() {
        if (userData == null) {
            loadUserDataFromPreferences();
        }
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
        saveUserDataToPreferences();
    }

    private void saveUserDataToPreferences() {
        if (userData != null) {
            prefs.put("uid", userData.getUid());
            prefs.put("name", userData.getName());
            prefs.put("apellidos", userData.getApellidos());
            prefs.put("role", userData.getRole());
            prefs.put("user", userData.getUser());
            prefs.put("CCT", userData.getCCT());
            prefs.put("escuela", userData.getEscuela());
            prefs.put("ciudad", userData.getCiudad());
            prefs.put("colonia", userData.getColonia());
            prefs.put("token", userData.getToken());
        }
    }

    private void loadUserDataFromPreferences() {
        String uid = prefs.get("uid", null);
        if (uid != null) {
            userData = new UserData();
            userData.setUid(uid);
            userData.setName(prefs.get("name", ""));
            userData.setApellidos(prefs.get("apellidos", ""));
            userData.setRole(prefs.get("role", ""));
            userData.setUser(prefs.get("user", ""));
            userData.setCCT(prefs.get("CCT", ""));
            userData.setEscuela(prefs.get("escuela", ""));
            userData.setCiudad(prefs.get("ciudad", ""));
            userData.setColonia(prefs.get("colonia", ""));
            userData.setToken(prefs.get("token", ""));
        }
    }

    public void clearSession() {
        try {
            prefs.remove("uid");
            prefs.remove("name");
            prefs.remove("apellidos");
            prefs.remove("role");
            prefs.remove("user");
            prefs.remove("CCT");
            prefs.remove("escuela");
            prefs.remove("ciudad");
            prefs.remove("colonia");
            prefs.remove("token");

            prefs.flush(); // Guardar los cambios
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
        this.userData = null;
    }



    public boolean isLoggedIn() {
        return getUserData() != null && getUserData().getToken() != null;
    }
}