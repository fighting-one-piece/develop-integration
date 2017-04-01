package org.cisiondata.modules.es.plugins.stconverter;

import java.util.Collection;
import java.util.Collections;

import org.cisiondata.modules.es.plugins.stconverter.analysis.STConvertAnalysisBinderProcessor;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.plugins.Plugin;

/**
 */
@SuppressWarnings("deprecation")
public class AnalysisSTConvertPlugin extends Plugin {

    @Override
    public String name() {
        return "analysis-stconvert";
    }

    @Override
    public String description() {
        return "convert chinese characters between traditional and simplified";
    }


    @Override
    public Collection<Module> nodeModules() {
        return Collections.<Module>singletonList(new STConvertIndicesAnalysisModule());
    }

    public void onModule(AnalysisModule module) {
        module.addProcessor(new STConvertAnalysisBinderProcessor());
    }
}
