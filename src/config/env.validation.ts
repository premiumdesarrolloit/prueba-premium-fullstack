import * as Joi from 'joi';

export const envValidationSchema = Joi.object({
  // App
  NODE_ENV: Joi.string().valid('development', 'qa', 'production').required(),
  PORT: Joi.number().default(3000),

  // Auth
  JWT_SECRET: Joi.string().min(16).required(),
  JWT_EXPIRES_IN: Joi.alternatives(
    Joi.string(),
    Joi.number()
  ).default('3600'),

  // DB
  DB_HOST: Joi.string().required(),
  DB_PORT: Joi.number().port().default(5432),
  DB_USER: Joi.string().required(),
  DB_PASS: Joi.string().allow('').required(),
  DB_NAME: Joi.string().required(),
  DB_SSL: Joi.boolean().truthy('true').falsy('false').default(false),

  POSTGRES_DB: Joi.string().required(),
  POSTGRES_USER: Joi.string().allow('').required(),
  POSTGRES_PASSWORD: Joi.string().required(),
});
