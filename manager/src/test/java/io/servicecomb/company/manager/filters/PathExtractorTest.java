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

package io.servicecomb.company.manager.filters;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.netflix.zuul.context.RequestContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class PathExtractorTest {

  private final RequestContext requestContext = Mockito.mock(RequestContext.class);
  private final HttpServletRequest servletRequest = Mockito.mock(HttpServletRequest.class);

  private final Map<String, List<String>> queryParams = new HashMap<>();
  private final PathExtractor extractor = new PathExtractor();

  @Before
  public void setUp() throws Exception {
    when(requestContext.getRequest()).thenReturn(servletRequest);
    when(servletRequest.getContextPath()).thenReturn("/root");
    when(servletRequest.getServletPath()).thenReturn("/path/to/resource");

    queryParams.put("n", asList("3", "5"));
  }

  @Test
  public void extractsPathWithQueryStringAndPathInfo() {
    when(servletRequest.getPathInfo()).thenReturn("/id");
    when(requestContext.getRequestQueryParams()).thenReturn(queryParams);

    String path = extractor.path(requestContext);

    assertThat(path).isEqualTo("/root/path/to/resource/id?n=3&n=5");
  }
}