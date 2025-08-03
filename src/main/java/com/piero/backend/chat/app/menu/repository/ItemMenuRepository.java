package com.piero.backend.chat.app.menu.repository;

import com.piero.backend.chat.app.menu.model.ItemMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ItemMenuRepository extends JpaRepository<ItemMenu, Integer> {
    List<ItemMenu> findItemMenuByActivo(boolean activo);
}
