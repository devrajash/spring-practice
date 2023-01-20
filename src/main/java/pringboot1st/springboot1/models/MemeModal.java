package pringboot1st.springboot1.models;

import org.springframework.data.mongodb.repository.MongoRepository;

import pringboot1st.springboot1.DbInterface.MemeInterface;

public interface MemeModal extends MongoRepository<MemeInterface, String> {

}
