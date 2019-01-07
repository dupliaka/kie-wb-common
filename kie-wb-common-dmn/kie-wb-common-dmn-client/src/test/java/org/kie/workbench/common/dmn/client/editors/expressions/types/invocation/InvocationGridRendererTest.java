/*
 * Copyright 2018 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.workbench.common.dmn.client.editors.expressions.types.invocation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InvocationGridRendererTest {

    @Mock
    private InvocationGridData gridData;

    private InvocationGridRenderer renderer;

    @Before
    public void setup() {
        this.renderer = new InvocationGridRenderer(gridData);
    }

    @Test
    public void testHeaderDimensionsWhenHeaderHasOneRow() {
        when(gridData.getHeaderRowCount()).thenReturn(1);

        assertEquals(InvocationGridRenderer.HEADER_ROW_HEIGHT,
                     renderer.getHeaderHeight(),
                     0.0);
        assertEquals(InvocationGridRenderer.HEADER_ROW_HEIGHT,
                     renderer.getHeaderRowHeight(),
                     0.0);
    }

    @Test
    public void testHeaderDimensionsWhenHeaderHasTwoRows() {
        when(gridData.getHeaderRowCount()).thenReturn(2);

        assertEquals(InvocationGridRenderer.HEADER_ROW_HEIGHT * 2,
                     renderer.getHeaderHeight(),
                     0.0);
        assertEquals(InvocationGridRenderer.HEADER_ROW_HEIGHT,
                     renderer.getHeaderRowHeight(),
                     0.0);
    }
}