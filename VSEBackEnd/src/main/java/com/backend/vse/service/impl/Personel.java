package com.backend.vse.service.impl;

import com.backend.vse.tools.Enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
public class Personel {
    private Roles PersonelRole;
    private final Set<TeachingActivity> TeachingPermission;
    private final Set<UserAndAccountActivity> AccountPermission;
}
