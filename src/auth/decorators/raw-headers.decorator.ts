import { createParamDecorator, ExecutionContext } from '@nestjs/common';

export const RawHeaders = createParamDecorator(
    ( _: string, ctx: ExecutionContext ) => {
        const req = ctx.switchToHttp().getRequest();
        return req.rawHeaders;
    }
);