package com.stolensugar.web.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import com.stolensugar.web.dagger.DaggerApplicationComponent;

public class UpdateActivityProvider  implements RequestHandler<ScheduledEvent, String> {

    public UpdateActivityProvider() {

    }

    @Override
    public String handleRequest(final ScheduledEvent event, Context context) {
        return DaggerApplicationComponent.create().provideUpdateActivity().handleRequest(event, context);
    }
}
