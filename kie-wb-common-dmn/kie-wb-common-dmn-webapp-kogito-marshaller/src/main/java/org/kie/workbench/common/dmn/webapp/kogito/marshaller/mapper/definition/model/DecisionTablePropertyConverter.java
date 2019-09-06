/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
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

package org.kie.workbench.common.dmn.webapp.kogito.marshaller.mapper.definition.model;

import java.util.Objects;

import jsinterop.base.Js;
import jsinterop.base.JsArrayLike;
import org.kie.workbench.common.dmn.api.definition.model.BuiltinAggregator;
import org.kie.workbench.common.dmn.api.definition.model.DecisionRule;
import org.kie.workbench.common.dmn.api.definition.model.DecisionTable;
import org.kie.workbench.common.dmn.api.definition.model.DecisionTableOrientation;
import org.kie.workbench.common.dmn.api.definition.model.HitPolicy;
import org.kie.workbench.common.dmn.api.definition.model.InputClause;
import org.kie.workbench.common.dmn.api.definition.model.OutputClause;
import org.kie.workbench.common.dmn.api.property.dmn.Description;
import org.kie.workbench.common.dmn.api.property.dmn.Id;
import org.kie.workbench.common.dmn.api.property.dmn.QName;
import org.kie.workbench.common.dmn.webapp.kogito.marshaller.js.model.dmn12.JSITBuiltinAggregator;
import org.kie.workbench.common.dmn.webapp.kogito.marshaller.js.model.dmn12.JSITDecisionRule;
import org.kie.workbench.common.dmn.webapp.kogito.marshaller.js.model.dmn12.JSITDecisionTable;
import org.kie.workbench.common.dmn.webapp.kogito.marshaller.js.model.dmn12.JSITDecisionTableOrientation;
import org.kie.workbench.common.dmn.webapp.kogito.marshaller.js.model.dmn12.JSITDefinitions;
import org.kie.workbench.common.dmn.webapp.kogito.marshaller.js.model.dmn12.JSITHitPolicy;
import org.kie.workbench.common.dmn.webapp.kogito.marshaller.js.model.dmn12.JSITInputClause;
import org.kie.workbench.common.dmn.webapp.kogito.marshaller.js.model.dmn12.JSITOutputClause;
import org.kie.workbench.common.dmn.webapp.kogito.marshaller.mapper.JsUtils;

public class DecisionTablePropertyConverter {

    public static DecisionTable wbFromDMN(final JSITDecisionTable dmn,
                                          final JSITDefinitions jsiDefinitions) {
        final Id id = new Id(dmn.getId());
        final Description description = DescriptionPropertyConverter.wbFromDMN(dmn.getDescription());
        final QName typeRef = QNamePropertyConverter.wbFromDMN(dmn.getTypeRef(), dmn, jsiDefinitions);

        final DecisionTable result = new DecisionTable();
        result.setId(id);
        result.setDescription(description);
        result.setTypeRef(typeRef);

        final JsArrayLike<JSITInputClause> wrappedInputClauses = dmn.getInput();
        if (Objects.nonNull(wrappedInputClauses)) {
            final JsArrayLike<JSITInputClause> jsiInputClauses = JsUtils.getUnwrappedElementsArray(wrappedInputClauses);
            for (int i = 0; i < jsiInputClauses.getLength(); i++) {
                final JSITInputClause input = Js.uncheckedCast(jsiInputClauses.getAt(i));
                final InputClause inputClauseConverted = InputClausePropertyConverter.wbFromDMN(input, jsiDefinitions);
                if (Objects.nonNull(inputClauseConverted)) {
                    inputClauseConverted.setParent(result);
                    result.getInput().add(inputClauseConverted);
                }
            }
        }
        final JsArrayLike<JSITOutputClause> wrappedOutputClauses = dmn.getOutput();
        if (Objects.nonNull(wrappedInputClauses)) {
            final JsArrayLike<JSITOutputClause> jsiOutputClauses = JsUtils.getUnwrappedElementsArray(wrappedOutputClauses);
            for (int i = 0; i < jsiOutputClauses.getLength(); i++) {
                final JSITOutputClause output = Js.uncheckedCast(jsiOutputClauses.getAt(i));
                final OutputClause outputClauseConverted = OutputClausePropertyConverter.wbFromDMN(output, jsiDefinitions);
                if (Objects.nonNull(outputClauseConverted)) {
                    outputClauseConverted.setParent(result);
                    result.getOutput().add(outputClauseConverted);
                }
            }
        }
        final JsArrayLike<JSITDecisionRule> wrappedDecisionRules = dmn.getRule();
        if (Objects.nonNull(wrappedDecisionRules)) {
            final JsArrayLike<JSITDecisionRule> jsiDecisionRules = JsUtils.getUnwrappedElementsArray(wrappedDecisionRules);
            for (int i = 0; i < jsiDecisionRules.getLength(); i++) {
                final JSITDecisionRule dr = Js.uncheckedCast(jsiDecisionRules.getAt(i));
                final DecisionRule decisionRuleConverted = DecisionRulePropertyConverter.wbFromDMN(dr, jsiDefinitions);
                if (decisionRuleConverted != null) {
                    decisionRuleConverted.setParent(result);
                }
                result.getRule().add(decisionRuleConverted);
            }
        }

        //JSITHitPolicy is a String JSO so convert into the real type
        final String hitPolicy = Js.uncheckedCast(dmn.getHitPolicy());
        if (Objects.nonNull(hitPolicy)) {
            result.setHitPolicy(HitPolicy.fromValue(hitPolicy));
        }

        //JSITBuiltinAggregator is a String JSO so convert into the real type
        final String aggregation = Js.uncheckedCast(dmn.getAggregation());
        if (Objects.nonNull(aggregation)) {
            result.setAggregation(BuiltinAggregator.fromValue(aggregation));
        }

        //JSITDecisionTableOrientation is a String JSO so convert into the real type
        final String orientation = Js.uncheckedCast(dmn.getPreferredOrientation());
        if (Objects.nonNull(orientation)) {
            result.setPreferredOrientation(DecisionTableOrientation.fromValue(orientation));
        }

        result.setOutputLabel(dmn.getOutputLabel());

        return result;
    }

    public static JSITDecisionTable dmnFromWB(final DecisionTable wb) {
        final JSITDecisionTable result = new JSITDecisionTable();
        result.setId(wb.getId().getValue());
        result.setDescription(DescriptionPropertyConverter.dmnFromWB(wb.getDescription()));
        QNamePropertyConverter.setDMNfromWB(wb.getTypeRef(), result::setTypeRef);

        for (InputClause input : wb.getInput()) {
            final JSITInputClause c = InputClausePropertyConverter.dmnFromWB(input);
            if (c != null) {
                c.setParent(result);
            }
            JsUtils.add(result.getInput(), c);
        }
        for (OutputClause input : wb.getOutput()) {
            final JSITOutputClause c = OutputClausePropertyConverter.dmnFromWB(input);
            if (c != null) {
                c.setParent(result);
            }
            JsUtils.add(result.getOutput(), c);
        }
        if (result.getOutput().getLength() == 1) {
            result.getOutput().getAt(0).setName(null); // DROOLS-3281
        }
        for (DecisionRule dr : wb.getRule()) {
            final JSITDecisionRule c = DecisionRulePropertyConverter.dmnFromWB(dr);
            if (c != null) {
                c.setParent(result);
            }
            JsUtils.add(result.getRule(), c);
        }
        if (wb.getHitPolicy() != null) {
            result.setHitPolicy(JSITHitPolicy.valueOf(wb.getHitPolicy().name()));
        }
        if (wb.getAggregation() != null) {
            result.setAggregation(JSITBuiltinAggregator.valueOf(wb.getAggregation().name()));
        }
        if (wb.getPreferredOrientation() != null) {
            result.setPreferredOrientation(JSITDecisionTableOrientation.valueOf(wb.getPreferredOrientation().name()));
        }

        result.setOutputLabel(wb.getOutputLabel());

        return result;
    }
}