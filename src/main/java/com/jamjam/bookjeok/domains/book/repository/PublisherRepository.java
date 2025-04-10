package com.jamjam.bookjeok.domains.book.repository;

import com.jamjam.bookjeok.domains.book.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    Optional<Publisher> findByPublisherName(String publisherName);

}
