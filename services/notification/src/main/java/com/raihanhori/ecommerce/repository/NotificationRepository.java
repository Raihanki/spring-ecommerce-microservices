package com.raihanhori.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raihanhori.ecommerce.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

}
