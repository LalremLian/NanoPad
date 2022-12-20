package com.lazydeveloper.nanopad.utilitites;

import com.lazydeveloper.nanopad.model.User;

public interface AdapterListener {
    void onUpdate(User user);
    void onDelete(int id, int position);
}
