package pringboot1st.springboot1.models;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import pringboot1st.springboot1.DbInterface.MemeInterface;

public interface MemeModal extends MongoRepository<MemeInterface, String> {
    public Optional<MemeInterface> findByIdName(String idName);

    public Optional<MemeInterface> deleteByIdName(String idName);
}
