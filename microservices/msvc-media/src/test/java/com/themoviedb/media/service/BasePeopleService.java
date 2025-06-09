package com.themoviedb.media.service;

import com.themoviedb.media.BasePeople;
import com.themoviedb.media.client.PeopleFeignClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BasePeopleService extends BasePeople {

    @Mock
    protected PeopleFeignClient peopleFeignClient;

    @InjectMocks
    protected PeopleService peopleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        peopleService = new PeopleService(peopleFeignClient);
    }
}
