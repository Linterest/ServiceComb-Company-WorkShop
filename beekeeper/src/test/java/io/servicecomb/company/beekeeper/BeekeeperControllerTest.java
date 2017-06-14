/*
 * Copyright 2017 Huawei Technologies Co., Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.servicecomb.company.beekeeper;

import static com.seanyinx.github.unit.scaffolding.Randomness.nextInt;
import static com.seanyinx.github.unit.scaffolding.Randomness.nextLong;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(BeekeeperController.class)
public class BeekeeperControllerTest {

  private final int generation = nextInt(90);
  private final long fibonacciValue = nextLong();

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private BeekeeperService beekeeperService;

  @Test
  public void getsAncestorsOfDroneUsingUnderlyingService() throws Exception {
    when(beekeeperService.ancestorsOfDroneAt(generation)).thenReturn(fibonacciValue);

    mockMvc.perform(get("/drone/ancestors/{generation}", generation))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.ancestors", is(fibonacciValue)));
  }

  @Test
  public void getsAncestorsOfQueenUsingUnderlyingService() throws Exception {
    when(beekeeperService.ancestorsOfQueenAt(generation)).thenReturn(fibonacciValue);

    mockMvc.perform(get("/queen/ancestors/{generation}", generation))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.ancestors", is(fibonacciValue)));
  }
}
