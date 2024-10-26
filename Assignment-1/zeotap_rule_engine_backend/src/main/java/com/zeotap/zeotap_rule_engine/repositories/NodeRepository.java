package com.zeotap.zeotap_rule_engine.repositories;

import com.zeotap.zeotap_rule_engine.models.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {
}
