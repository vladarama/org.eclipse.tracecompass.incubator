/*******************************************************************************
 * Copyright (c) 2024 École Polytechnique de Montréal
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License 2.0 which
 * accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.tracecompass.incubator.rocm.core.trace;

import java.util.Collection;

import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tracecompass.tmf.core.event.ITmfEvent;
import org.eclipse.tracecompass.tmf.core.event.aspect.ITmfEventAspect;
import org.eclipse.tracecompass.tmf.core.event.aspect.TmfBaseAspects;
import org.eclipse.tracecompass.tmf.core.exceptions.TmfTraceException;
import org.eclipse.tracecompass.tmf.ctf.core.trace.CtfTmfTrace;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableSet;

/**
 * Traces generated by the ROCm environment (ROC-tracer and/or ROC-profiler)
 * that use the CTF format. These traces are identified using the tracer_name
 * environment variable.
 *
 * @author Arnaud Fiorini
 */
public class RocmTrace extends CtfTmfTrace {

    private static final Collection<ITmfEventAspect<?>> ROCM_CTF_ASPECTS = ImmutableList.of(
            TmfBaseAspects.getTimestampAspect(),
            TmfBaseAspects.getEventTypeAspect(),
            TmfBaseAspects.getContentsAspect(),
            TmfBaseAspects.getTraceNameAspect());

    /** Collection of aspects, default values */
    private Collection<ITmfEventAspect<?>> fAspects = ImmutableSet.copyOf(ROCM_CTF_ASPECTS);

    @Override
    public Iterable<ITmfEventAspect<?>> getEventAspects() {
        return fAspects;
    }

    @Override
    public void initTrace(final @Nullable IResource resource, final @Nullable String path,
            final @Nullable Class <? extends @Nullable ITmfEvent> eventType) throws TmfTraceException {
        super.initTrace(resource, path, eventType);

        ImmutableList.Builder<ITmfEventAspect<?>> builder = new Builder<>();
        builder.addAll(ROCM_CTF_ASPECTS);
        fAspects = builder.build();
    }
}
