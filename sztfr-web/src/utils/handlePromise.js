import { PromiseResult } from "models";

/**
 *
 * @param promise
 * @returns {Promise<PromiseResult>}
 */
export default async function handlePromise(promise) {
  try {
    const data = await promise;
    return new PromiseResult(data, null);
  } catch (error) {
    return new PromiseResult(null, error);
  }
}
