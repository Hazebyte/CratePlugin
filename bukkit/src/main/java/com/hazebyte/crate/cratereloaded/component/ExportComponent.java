package com.hazebyte.crate.cratereloaded.component;

import lombok.NonNull;
import org.bukkit.entity.Player;

public interface ExportComponent {

    void exportItem(@NonNull Player player);

    void exportCrate(@NonNull Player player, String fileName, String sort);
}
